package fr.bretzel.bcore.nms.entity;

import fr.bretzel.bcore.nms.World;
import fr.bretzel.bcore.utils.Reflection;
import org.bukkit.Location;

import java.lang.reflect.Constructor;

public class MCArmorStand extends MCEntity
{
    public MCArmorStand(Location location)
    {
        super(location);
        Class<?> entity_armor_stand = Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "EntityArmorStand");
        World world = new World(location.getWorld());
        Constructor<?> armor_stand_constructor = Reflection.getConstructor(entity_armor_stand, world.getWorld().getClass(), Double.TYPE, Double.TYPE, Double.TYPE);

        setNmsEntityObject(Reflection.newInstance(armor_stand_constructor, world.getWorld(), getLocation().getX(), getLocation().getY(), getLocation().getZ()));

        initMethod();
    }
}
