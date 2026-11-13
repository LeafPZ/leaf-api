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

package dev.aoqia.leaf.api.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import dev.aoqia.leaf.api.events.CharacterBiteEvent;
import dev.aoqia.leaf.api.events.CharacterDamageEvent;
import dev.aoqia.leaf.api.events.CharacterInfectEvent;
import dev.aoqia.leaf.api.events.ClientConnectEvent;
import dev.aoqia.leaf.api.events.ClientDisconnectEvent;
import dev.aoqia.leaf.api.events.ItemConditionModifyEvent;
import dev.aoqia.leaf.api.events.PostSandboxOptionsLoadEvent;
import dev.aoqia.leaf.api.events.PreSandboxOptionsLoadEvent;
import dev.aoqia.leaf.api.events.VehiclePartDamageEvent;

import zombie.Lua.LuaEventManager;

@SuppressWarnings({ "UnusedMethod", "UnusedVariable" })
@Mixin(LuaEventManager.class)
public class LuaEventManagerMixin {
    @Inject(method = "AddEvents", at = @At("TAIL"))
    private static void AddEvents(CallbackInfo ci) {
        CharacterBiteEvent.EVENT.add();
        CharacterDamageEvent.EVENT.add();
        CharacterInfectEvent.EVENT.add();
        ClientConnectEvent.EVENT.add();
        ClientDisconnectEvent.EVENT.add();
        ClientDisconnectEvent.EVENT.add();
        ItemConditionModifyEvent.EVENT.add();
        PostSandboxOptionsLoadEvent.EVENT.add();
        PreSandboxOptionsLoadEvent.EVENT.add();
        VehiclePartDamageEvent.EVENT.add();
    }
}
