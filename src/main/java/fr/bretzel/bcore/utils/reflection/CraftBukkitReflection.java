package fr.bretzel.bcore.utils.reflection;

import fr.bretzel.bcore.BCore;

import java.lang.reflect.Method;

public class CraftBukkitReflection
{
    public static Class<?> CLASS_PLAYER;
    public static Class<?> CLASS_WORLD;

    public static Method METHOD_WORLD_GET_HANDLE;
    public static Method METHOD_PLAYER_GET_HANDLE;

    static
    {
        BCore.INSTANCE.logInfo("ReflectionUtils.CraftBukkit.class initialization");

        /////////////////////
        //Class definition//
        ////////////////////
        CLASS_PLAYER = Reflection.getClass(Reflection.ClassType.CRAFT_BUKKIT_ENTITY, "CraftPlayer");
        CLASS_WORLD = Reflection.getClass(Reflection.ClassType.CRAFT_BUKKIT, "CraftWorld");

        /////////////////////
        //Method definition//
        ////////////////////
        METHOD_PLAYER_GET_HANDLE = Reflection.getMethod(CLASS_PLAYER, "getHandle");
        METHOD_WORLD_GET_HANDLE = Reflection.getMethod(CLASS_WORLD, "getHandle");
    }
}
