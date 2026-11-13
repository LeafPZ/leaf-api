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

import dev.aoqia.leaf.api.Main;

/// Fires when a client has disconnected from the server.
/// @apiNote Only triggers on the server
public final class ClientDisconnectEvent extends Event<ClientDisconnectEvent.Listener> {
    public static final ClientDisconnectEvent EVENT = new ClientDisconnectEvent();

    ClientDisconnectEvent() {
        super(Main.MOD_ID, "client_disconnect", false, true);
    }

    /// Triggers the event which causes all the registered callbacks (both in Java and Lua) to be executed.
    /// @param idStr the client idStr
    public static void trigger(String idStr) {
        EVENT.triggerInternal(idStr);
        EVENT.dispatch(listener -> listener.apply(idStr));
    }

    public interface Listener {
        void apply(String idStr);
    }
}
