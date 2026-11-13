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

/// Fires before the sandbox options load.
public final class PreSandboxOptionsLoadEvent extends Event<PreSandboxOptionsLoadEvent.Listener> {
    public static final PreSandboxOptionsLoadEvent EVENT = new PreSandboxOptionsLoadEvent();

    PreSandboxOptionsLoadEvent() {
        super(Main.MOD_ID, "pre_sandbox_options_load", true, true);
    }

    /// Triggers the event which causes all the registered callbacks (both in Java and Lua) to be executed.
    public static void trigger() {
        EVENT.triggerInternal();
        EVENT.dispatch(Listener::apply);
    }

    public interface Listener {
        void apply();
    }
}
