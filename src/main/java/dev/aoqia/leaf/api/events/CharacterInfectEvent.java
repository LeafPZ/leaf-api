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

import zombie.characters.IsoGameCharacter;

/// Fires when a character has been infected.
public class CharacterInfectEvent extends Event<CharacterInfectEvent.Listener> {
    public static final CharacterInfectEvent EVENT = new CharacterInfectEvent();

    CharacterInfectEvent() {
        super(Main.MOD_ID, "character_infect", true, true);
    }

    /// Triggers the event which causes all the registered callbacks (both in Java and Lua) to be executed.
    /// @param character the infected character
    public static void trigger(IsoGameCharacter character) {
        EVENT.triggerInternal(character);
        EVENT.dispatch(listener -> listener.apply(character));
    }

    public interface Listener {
        void apply(IsoGameCharacter character);
    }
}
