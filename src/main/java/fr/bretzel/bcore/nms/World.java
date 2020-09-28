package fr.bretzel.bcore.nms;

import fr.bretzel.bcore.utils.Reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class World
{
    private final org.bukkit.World bukkitWorld;

    private static final Class<?> craftworld_class;
    private static final Method get_handle_method;

    static
    {
        craftworld_class = Reflection.getClass(Reflection.ClassType.CRAFT_BUKKIT, "CraftWorld");
        get_handle_method = Reflection.getMethod(craftworld_class, "getHandle");
    }

    public World(org.bukkit.World world)
    {
        this.bukkitWorld = world;
    }

    public org.bukkit.World getBukkitWorld()
    {
        return bukkitWorld;
    }

    public Object getCraftWorld()
    {
        return craftworld_class.cast(getBukkitWorld());
    }

    public Object getWorld()
    {
        try
        {
            return get_handle_method.invoke(getCraftWorld());
        } catch (IllegalAccessException | InvocationTargetException e)
        {
            return null;
        }
    }
}
