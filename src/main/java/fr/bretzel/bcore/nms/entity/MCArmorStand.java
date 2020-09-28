package fr.bretzel.bcore.nms.entity;

import fr.bretzel.bcore.nms.World;
import fr.bretzel.bcore.utils.reflection.EntityReflection;
import fr.bretzel.bcore.utils.reflection.Reflection;
import org.bukkit.Location;

public class MCArmorStand extends MCEntity
{
    public MCArmorStand(Location location)
    {
        super(location);

        setNmsEntityObject(Reflection.newInstance(EntityReflection.CONSTRUCTOR_ENTITY_ARMOR_STAND, new World(location.getWorld()).getWorld(), getLocation().getX(), getLocation().getY(), getLocation().getZ()));
    }
}
