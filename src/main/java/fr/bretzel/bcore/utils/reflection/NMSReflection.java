package fr.bretzel.bcore.utils.reflection;

import fr.bretzel.bcore.BCore;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class NMSReflection
{
    public static Class<?> CLASS_PACKET;
    public static Class<?> CLASS_WORLD;

    public static Field FIELD_PLAYER_CONNECTION;
    public static Method METHOS_SEND_PACKET;

    static
    {
        BCore.INSTANCE.logInfo("ReflectionUtils.class initialization");

        /////////////////////
        //Class definition//
        ////////////////////
        CLASS_PACKET = Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "Packet");
        CLASS_WORLD = Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "World");


        ////////////////////
        //Field definition//
        ///////////////////
        FIELD_PLAYER_CONNECTION = Reflection.getField(EntityReflection.CLASS_ENTITY_PLAYER, "playerConnection");

        /////////////////////
        //Method definition//
        ////////////////////
        assert FIELD_PLAYER_CONNECTION != null;
        METHOS_SEND_PACKET = Reflection.getMethod(FIELD_PLAYER_CONNECTION.getType(), "sendPacket", CLASS_PACKET);
    }
}
