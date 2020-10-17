package fr.bretzel.bcore.nms.entity;

import fr.bretzel.bcore.nms.BWorld;
import fr.bretzel.bcore.utils.Reflection;
import fr.bretzel.bcore.utils.reflection.EntityReflection;
import org.bukkit.Location;

public class MCArmorStand extends MCEntity
{
    public MCArmorStand(Location location)
    {
        super(location);

        setNmsEntityObject(Reflection.newInstance(EntityReflection.CONSTRUCTOR_ENTITY_ARMOR_STAND, new BWorld(location.getWorld()).getNMSWorld(), getLocation().getX(), getLocation().getY(), getLocation().getZ()));
    }
}
