package fr.bretzel.bcore.utils.reflection;

import fr.bretzel.bcore.utils.Reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class EntityReflection
{
    public static Class<?> CLASS_ENTITY_PLAYER;
    public static Class<?> CLASS_ENTITY;
    public static Class<?> CLASS_ENTITY_ARMOR_STAND;

    public static Constructor<?> CONSTRUCTOR_ENTITY_ARMOR_STAND;

    public static Method METHOD_SET_LOCATION;
    public static Method METHOD_SET_NO_GRAVITY;
    public static Method METHOD_GET_ID;

    static
    {
        System.out.println("EntityReflection.class initialization");

        /////////////////////
        //Class definition//
        ////////////////////
        CLASS_ENTITY_PLAYER = Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "EntityPlayer");
        CLASS_ENTITY = Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "Entity");
        CLASS_ENTITY_ARMOR_STAND = Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "EntityArmorStand");

        //////////////////////////
        //Constructor definition//
        //////////////////////////
        CONSTRUCTOR_ENTITY_ARMOR_STAND = Reflection.getConstructor(CLASS_ENTITY_ARMOR_STAND, NMSReflection.CLASS_WORLD, Double.TYPE, Double.TYPE, Double.TYPE);

        /////////////////////
        //Method definition//
        ////////////////////
        METHOD_SET_LOCATION = Reflection.getMethod(CLASS_ENTITY, "setLocation", Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE);
        METHOD_SET_NO_GRAVITY = Reflection.getMethod(CLASS_ENTITY, "setNoGravity", Boolean.TYPE);
        METHOD_GET_ID = Reflection.getMethod(CLASS_ENTITY, "getId");
    }

    public static class Player
    {
        public static Class<?> CLASS_PLAYER_CONNECTION;

        public static Field FIELD_PLAYER_CONNECTION;
        public static Field FIELD_NETWORK_MANAGER;
        public static Field FIELD_PING;

        public static Method METHOD_SEND_PACKET;

        static
        {
            System.out.println("EntityReflection.Player.class initialization");

            /////////////////////
            //Class definition//
            ////////////////////
            CLASS_PLAYER_CONNECTION = Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "PlayerConnection");

            ////////////////////
            //Field definition//
            ///////////////////
            FIELD_PLAYER_CONNECTION = Reflection.getField(EntityReflection.CLASS_ENTITY_PLAYER, "playerConnection");
            FIELD_NETWORK_MANAGER = Reflection.getField(Player.CLASS_PLAYER_CONNECTION, "networkManager");
            FIELD_PING = Reflection.getField(CLASS_ENTITY_PLAYER, "ping");

            /////////////////////
            //Method definition//
            ////////////////////
            METHOD_SEND_PACKET = Reflection.getMethod(CLASS_PLAYER_CONNECTION, "sendPacket", NMSReflection.CLASS_PACKET);
        }
    }
}