package fr.bretzel.bcore.utils.reflection;

import fr.bretzel.bcore.utils.Reflection;
import org.bukkit.Material;

import java.lang.reflect.Method;

public class CraftBukkitReflection
{
    public static Class<?> CLASS_PLAYER;
    public static Class<?> CLASS_WORLD;
    public static Class<?> CLASS_CRAFT_BLOCK;
    public static Class<?> CLASS_CRAFT_MAGIC_NUMBER;

    public static Method METHOD_WORLD_GET_HANDLE;
    public static Method METHOD_PLAYER_GET_HANDLE;
    public static Method METHOD_MAGIC_GET_BLOCK;

    static
    {
        System.out.println("ReflectionUtils.CraftBukkit.class initialization");

        /////////////////////
        //Class definition//
        ////////////////////
        CLASS_PLAYER = Reflection.getClass(Reflection.ClassType.CRAFT_BUKKIT_ENTITY, "CraftPlayer");
        CLASS_WORLD = Reflection.getClass(Reflection.ClassType.CRAFT_BUKKIT, "CraftWorld");
        CLASS_CRAFT_BLOCK = Reflection.getClass(Reflection.ClassType.CRAFT_BUKKIT_BLOCK, "CraftBlock");
        CLASS_CRAFT_MAGIC_NUMBER = Reflection.getClass(Reflection.ClassType.CRAFT_BUKKIT_UTIL, "CraftMagicNumbers");

        /////////////////////
        //Method definition//
        ////////////////////
        METHOD_PLAYER_GET_HANDLE = Reflection.getMethod(CLASS_PLAYER, "getHandle");
        METHOD_WORLD_GET_HANDLE = Reflection.getMethod(CLASS_WORLD, "getHandle");
        METHOD_MAGIC_GET_BLOCK = Reflection.getMethod(CLASS_CRAFT_MAGIC_NUMBER, "getBlock", Material.class);
    }
}
