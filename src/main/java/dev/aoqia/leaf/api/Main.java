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

package dev.aoqia.leaf.api;

import dev.aoqia.leaf.api.events.CharacterDamageEvent;
import dev.aoqia.leaf.api.game.PowerApi;
import dev.aoqia.leaf.api.lua.ExposerApi;
import dev.aoqia.leaf.loader.impl.LeafLoaderImpl;

import zombie.debug.DebugType;

public class Main implements ModInitializer {
    public static final boolean IS_DEVELOPMENT = LeafLoaderImpl.INSTANCE.isDevelopmentEnvironment();
    public static final String MOD_ID = "leaf-api";

    @Override
    public void onInitialize() {
        CharacterDamageEvent.EVENT.register(character -> {
            DebugType.Mod.println("Character '%s' was damaged :c".formatted(character.getFullName()));
        });

        exposeClasses();
    }

    private void exposeClasses() {
        ExposerApi.exposeClass(PowerApi.class);
    }
}
