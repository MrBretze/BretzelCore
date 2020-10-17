package fr.bretzel.bcore.utils.reflection;

import fr.bretzel.bcore.utils.Reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

public class NMSReflection
{
    public static Class<?> CLASS_PACKET;
    public static Class<?> CLASS_WORLD;
    public static Class<?> CLASS_NETWORK_MANAGER;
    public static Class<?> CLASS_BLOCKS;

    public static Class<?> CLASS_VEC3D;
    public static Class<?> CLASS_MOVING_OBJECT_POSITION_BLOCK;

    public static Field FIELD_NETWORK_MANAGER_CHANNEL;

    public static Constructor<?> CONSTRUCTOR_VEC3D;

    static
    {
        System.out.println("ReflectionUtils.class initialization");

        /////////////////////
        //Class definition//
        ////////////////////
        CLASS_PACKET = Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "Packet");
        CLASS_WORLD = Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "World");
        CLASS_NETWORK_MANAGER = Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "NetworkManager");
        CLASS_VEC3D = Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "Vec3D");
        CLASS_MOVING_OBJECT_POSITION_BLOCK = Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "MovingObjectPositionBlock");
        CLASS_BLOCKS = Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "Blocks");
        ////////////////////
        //Field definition//
        ///////////////////
        FIELD_NETWORK_MANAGER_CHANNEL = Reflection.getField(CLASS_NETWORK_MANAGER, "channel");
        //////////////////////////
        //Constructor definition//
        //////////////////////////
        CONSTRUCTOR_VEC3D = Reflection.getConstructor(CLASS_VEC3D, Double.TYPE, Double.TYPE, Double.TYPE);
    }
}
