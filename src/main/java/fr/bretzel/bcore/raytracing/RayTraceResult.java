package fr.bretzel.bcore.raytracing;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class RayTraceResult
{
    private final List<EntityHitResult> entities;
    private final List<Location> locations;
    private final Vector direction;
    private final Location endLocation;
    private final Location startLocation;

    public RayTraceResult(Location startLocation, Location hitLocation, Vector direction)
    {
        this.startLocation = startLocation;
        this.endLocation = hitLocation;
        this.locations = new ArrayList<>();
        this.entities = new ArrayList<>();
        this.direction = direction;
    }

    public RayTraceResult(Location startLocation, Location hitLocation, Vector direction, List<EntityHitResult> entities, List<Location> locations)
    {
        this.startLocation = startLocation;
        this.endLocation = hitLocation;
        this.locations = locations;
        this.entities = entities;
        this.direction = direction;
    }

    public Vector getDirection()
    {
        return direction;
    }

    public List<EntityHitResult> getEntities()
    {
        return entities;
    }

    public Location getHitLocation()
    {
        return endLocation;
    }

    public Location getStartLocation()
    {
        return startLocation;
    }

    public List<Location> getLocations()
    {
        return locations;
    }
}
