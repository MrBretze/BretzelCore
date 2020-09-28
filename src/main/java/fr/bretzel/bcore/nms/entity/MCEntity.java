package fr.bretzel.bcore.nms.entity;

import fr.bretzel.bcore.nms.World;
import fr.bretzel.bcore.utils.Reflection;
import org.bukkit.Location;

import java.lang.reflect.Method;

public abstract class MCEntity
{
    private Object nms_entity_object = null;
    private final World world;
    private final Location location;


    //Method and Class registration
    private final Class<?> entity_class = Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "Entity");
    private final Method set_location = Reflection.getMethod(entity_class, "setLocation", Double.TYPE, Double.TYPE, Double.TYPE, Float.TYPE, Float.TYPE);
    private final Method set_no_gravity = Reflection.getMethod(entity_class, "setNoGravity", Boolean.TYPE);
    private final Method getId = Reflection.getMethod(entity_class, "getId");

    MCEntity(Location location)
    {
        this.location = location;
        this.world = new World(location.getWorld());
    }

    public Location getLocation()
    {
        return location;
    }

    public World getWorld()
    {
        return world;
    }

    public Object toNMSEntity()
    {
        return nms_entity_object;
    }

    protected void setNmsEntityObject(Object nms_entity_object)
    {
        this.nms_entity_object = nms_entity_object;
    }

    protected void initMethod()
    {

    }

    public void setLocation(double x, double y, double z, float pitch, float yaw)
    {
        if (toNMSEntity() != null)
            Reflection.invoke(set_location, toNMSEntity(), x, y, z, pitch, yaw);
    }

    public void setNoGravity(boolean b)
    {
        if (toNMSEntity() != null)
            Reflection.invoke(set_no_gravity, toNMSEntity(), b);
    }

    public int getId()
    {
        if (toNMSEntity() != null)
            return (int) Reflection.invoke(getId, toNMSEntity());
        return 0;
    }
}
