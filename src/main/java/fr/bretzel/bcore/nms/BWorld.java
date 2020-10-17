package fr.bretzel.bcore.nms;

import fr.bretzel.bcore.utils.reflection.CraftBukkitReflection;

import java.lang.reflect.InvocationTargetException;

public class BWorld
{
    private final org.bukkit.World bukkitWorld;

    public BWorld(org.bukkit.World world)
    {
        this.bukkitWorld = world;
    }

    public org.bukkit.World getBukkitWorld()
    {
        return bukkitWorld;
    }

    public Object getCraftWorld()
    {
        return CraftBukkitReflection.CLASS_WORLD.cast(getBukkitWorld());
    }

    public Object getNMSWorld()
    {
        try
        {
            return CraftBukkitReflection.METHOD_WORLD_GET_HANDLE.invoke(getCraftWorld());
        } catch (IllegalAccessException | InvocationTargetException e)
        {
            return null;
        }
    }
}
