// checkstyle:off

/**
 * Copyright (C) 2026 aoqia
 *
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU Affero General
 * Public License as published by the Free Software Foundation, either version 3 of the License, or any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Affero General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Affero General Public License along with this program. If not,
 * see https://www.gnu.org/licenses/.
 *
 * This license and file are additionally subjected to the exceptions described in LICENSE_EXCEPTION.md.
 *
 * The exceptions permit independent software to use this project as a dependency under terms of its own choosing, but
 * does not permit the copying or incorporation of covered source code into another work outside the terms of the AGPL.
 */

// checkstyle:on

package dev.aoqia.leaf.api.events;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import dev.aoqia.leaf.api.Main;
import dev.aoqia.leaf.api.mixin.LuaEventManagerAccessor;

import zombie.Lua.LuaEventManager;
import zombie.debug.DebugType;
import zombie.network.GameClient;
import zombie.network.GameServer;
import zombie.scripting.objects.ResourceLocation;

public abstract class Event<L> {
    private final List<L> listeners = new ArrayList<>();

    private final ResourceLocation id;
    private final boolean allowClient;
    private final boolean allowServer;

    private boolean canceled = false;

    /// Constructs a new event object.
    /// @param namespace the mod ID
    /// @param name the event name
    /// @param allowClient is the event allowed on the client
    /// @param allowServer is the event allowed on the server
    public Event(String namespace, String name, boolean allowClient, boolean allowServer) {
        this.id = new ResourceLocation(namespace, name);
        this.allowClient = allowClient;
        this.allowServer = allowServer;
    }

    /// Adds the event to the game's event list.
    /// @apiNote Call this only after Lua has initialized
    public void add() {
        LuaEventManager.AddEvent(this.id.toString());
    }

    /// Marks the event as canceled. When the event was canceled will determine execution.
    public void cancel() {
        canceled = true;
    }

    /// Returns whether the event has been added already to the event manager.
    /// @return true if added else false.
    public boolean isAdded() {
        return LuaEventManagerAccessor.getEventMap().containsKey(this.id.toString());
    }

    /// Registers a listener for the event that gets called when the event is triggered.
    /// @param listener the listener
    public void register(L listener) {
        if (listener == null) {
            return;
        }

        listeners.add(listener);
    }

    /// Unregisters a listener for the event.
    /// @param listener the listener
    public void unregister(L listener) {
        listeners.remove(listener);
    }

    /// Dispatches to registered listeners.
    /// @param action the listener action
    protected void dispatch(Consumer<L> action) {
        if (!isAdded()) {
            return;
        }

        for (L listener : listeners) {
            if (canceled) {
                return;
            }

            // Wrap just in case a listener dies (don't break my damn event system!)
            try {
                action.accept(listener);
            } catch (Exception e) {
                DebugType.General.error("An exception was caused by a registered event listener for event '%s'"
                    .formatted(this.getClass().getSimpleName()));
            }
        }
    }

    /// Triggers the event which causes all the registered callbacks (both in Java and Lua) to be executed.
    /// @param args the event args to pass to the listeners
    protected void triggerInternal(Object... args) {
        if (!isAdded()) {
            DebugType.Mod.warn("Event '%s' called but it wasn't added yet!".formatted(id));
            return;
        }

        if (Main.IS_DEVELOPMENT) {
            try {
                DebugType.Mod.println(
                    "[%s] Event '%s' called with args '%s'".formatted(Main.MOD_ID, id, Arrays.toString(args)));
            } catch (Exception _) {
                DebugType.Mod.println("[%s] Event '%s' called (but threw an exc)".formatted(Main.MOD_ID, id));
            }
        }

        if (GameClient.client && !allowClient) {
            DebugType.Mod.warn("Event '%s' was called in disallowed environment 'client'".formatted(id));
            return;
        }

        if (GameServer.server && !allowServer) {
            DebugType.Mod.warn("Event '%s' was called in disallowed environment 'server'".formatted(id));
            return;
        }

        LuaEventManager.triggerEvent(id.toString(), args);
    }
}
