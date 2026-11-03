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

import dev.aoqia.leaf.api.Events;

import zombie.Lua.LuaEventManager;
import zombie.SandboxOptions;

@Mixin(SandboxOptions.class)
public class SandboxOptionsMixin {
    @Inject(method = "load()V", at = @At("TAIL"))
    private void postLoad(CallbackInfo ci) {
        LuaEventManager.triggerEvent(Events.POST_SANDBOX_OPTIONS_LOAD.name());
    }

    @Inject(method = "load()V", at = @At("HEAD"))
    private void preLoad(CallbackInfo ci) {
        LuaEventManager.triggerEvent(Events.PRE_SANDBOX_OPTIONS_LOAD.name());
    }
}
