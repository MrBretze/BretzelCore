package fr.bretzel.bcore.nms.entity;

import fr.bretzel.bcore.nms.BWorld;
import fr.bretzel.bcore.utils.Reflection;
import fr.bretzel.bcore.utils.reflection.EntityReflection;
import org.bukkit.Location;

public abstract class MCEntity
{
    private final BWorld BWorld;
    private final Location location;
    private Object nms_entity_object = null;

    protected MCEntity(Location location)
    {
        this.location = location;
        this.BWorld = new BWorld(location.getWorld());
    }

    public Location getLocation()
    {
        return location;
    }

    public BWorld getWorld()
    {
        return BWorld;
    }

    public Object toNMSEntity()
    {
        return nms_entity_object;
    }

    protected void setNmsEntityObject(Object nms_entity_object)
    {
        this.nms_entity_object = nms_entity_object;
    }

    public void setLocation(double x, double y, double z, float pitch, float yaw)
    {
        if (toNMSEntity() != null)
            Reflection.invoke(EntityReflection.METHOD_SET_LOCATION, toNMSEntity(), x, y, z, pitch, yaw);
    }

    public void setNoGravity(boolean b)
    {
        if (toNMSEntity() != null)
            Reflection.invoke(EntityReflection.METHOD_SET_NO_GRAVITY, toNMSEntity(), b);
    }

    public int getId()
    {
        if (toNMSEntity() != null)
        {
            try
            {
                return (int) Reflection.invoke(EntityReflection.METHOD_GET_ID, toNMSEntity());
            } catch (Exception e)
            {
                return 0;
            }
        }
        return 0;
    }
}
