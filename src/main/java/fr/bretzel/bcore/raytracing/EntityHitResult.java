package fr.bretzel.bcore.raytracing;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.Vector;

public class EntityHitResult
{
    private final Entity hitedEntity;
    private final Location hitedLocation;
    private final Vector direction;

    public EntityHitResult(Entity entity, Vector direction, Location hitLocation)
    {
        this.hitedEntity = entity;
        this.direction = direction;
        this.hitedLocation = hitLocation;
    }

    public Entity getHitedEntity()
    {
        return hitedEntity;
    }

    public Vector getDirection()
    {
        return direction;
    }

    public Location getHitedLocation()
    {
        return hitedLocation;
    }
}
