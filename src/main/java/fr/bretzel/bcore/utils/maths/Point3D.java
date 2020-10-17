package fr.bretzel.bcore.utils.maths;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

public class Point3D extends Point2D
{
    private double z;

    public Point3D(double x, double y, double z)
    {
        super(x, y);
        this.z = z;
    }

    public Point3D crossProduct(Point3D other)
    {
        double newX = getY() * other.getZ() - other.getY() * getZ();
        double newY = getZ() * other.getX() - other.getZ() * getX();
        double newZ = getX() * other.getY() - other.getX() * getY();

        return new Point3D(newX, newY, newZ);
    }

    public double getZ()
    {
        return z;
    }

    public void setZ(double z)
    {
        this.z = z;
    }

    public Vector toVector()
    {
        return new Vector(getX(), getY(), getZ());
    }

    public Location toLocation(World world)
    {
        return new Location(world, getX(), getY(), getZ());
    }

    @Override
    public String toString()
    {
        return "Point3D[" +
                "x=" + getX() +
                ", y=" + getY() +
                ", z=" + getZ() +
                ']';
    }
}
