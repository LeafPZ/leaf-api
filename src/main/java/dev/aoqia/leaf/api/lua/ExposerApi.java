package dev.aoqia.leaf.api.lua;

import zombie.Lua.LuaManager;

public class ExposerApi {
    public static void exposeClass(Class<?> clazz) {
        LuaManager.exposer.setExposed(clazz);
    }
}
