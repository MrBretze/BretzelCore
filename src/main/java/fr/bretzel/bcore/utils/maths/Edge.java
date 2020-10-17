package fr.bretzel.bcore.utils.maths;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Edge
{
    private final Point3D vector_max;
    private final Point3D vector_min;

    public Edge(Vector vector_1, Vector vector_2)
    {
        this.vector_max = new Point3D(Math.max(vector_1.getX(), vector_2.getX()), Math.max(vector_1.getY(), vector_2.getY()), Math.max(vector_1.getZ(), vector_2.getZ()));
        this.vector_min = new Point3D(Math.min(vector_1.getX(), vector_2.getX()), Math.min(vector_1.getY(), vector_2.getY()), Math.min(vector_1.getZ(), vector_2.getZ()));
    }

    public Edge(Point3D point_1, Point3D point_2)
    {
        this.vector_max = new Point3D(Math.max(point_1.getX(), point_2.getX()), Math.max(point_1.getY(), point_2.getY()), Math.max(point_1.getZ(), point_2.getZ()));
        this.vector_min = new Point3D(Math.min(point_1.getX(), point_2.getX()), Math.min(point_1.getY(), point_2.getY()), Math.min(point_1.getZ(), point_2.getZ()));
    }

    public Point3D getMaxPoint()
    {
        return vector_max;
    }

    public Point3D getMinPoint()
    {
        return vector_min;
    }

    public boolean isInEdge(Point3D other)
    {
        return ((other.getY() - getMinPoint().getY()) / (other.getX() - getMinPoint().getX()) == (other.getY() - getMaxPoint().getY()) / (other.getX() - getMaxPoint().getY()));
    }

    public List<Location> getPointListBetween(World world)
    {
        List<Location> locations = new ArrayList<>();
        Vector min = getMinPoint().toVector();
        Vector max = getMaxPoint().toVector();
        Vector direction = min.clone().subtract(max.clone()).normalize().divide(new Vector(0.1, 0.1, 0.1));
        double distance = min.distance(max);
        Location start = new Location(world, min.getX(), min.getY(), min.getZ());
        Location end = new Location(world, max.getX(), max.getY(), max.getZ());

        while (start.distance(end) < distance)
        {
            start.add(direction);
            locations.add(start.clone());
        }

        return locations;
    }

    /*
        public boolean isOnSegment(Point3D other)
    {
        return other.getX() >= getVectorMin().getX() && other.getX() <= getVectorMax().getX()
                && other.getY() >= getVectorMin().getY() && other.getY() <= getVectorMax().getY()
                && other.getZ() >= getVectorMin().getZ() && other.getZ() <= getVectorMax().getZ();
    }
     */
}
