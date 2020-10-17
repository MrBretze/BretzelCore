package fr.bretzel.bcore.raytracing.boundingbox.box;

import fr.bretzel.bcore.utils.maths.Edge;
import fr.bretzel.bcore.utils.maths.Point2D;
import fr.bretzel.bcore.utils.maths.Point3D;
import fr.bretzel.bcore.utils.maths.Polygon;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public abstract class PointingBox extends BoundingBox
{
    private Edge[] edges;
    private Vector min, max;

    public PointingBox()
    {
        super();

        this.max = getPoints()[0].toVector();
        this.min = getPoints()[4].toVector();

        sync();
    }

    @Override
    public boolean isInside(Vector position)
    {
        ArrayList<Point2D> points = new ArrayList<>();
        for (Edge edge : getEdges())
        {
            if ((edge.getMaxPoint().getY() >= position.getY()) && (edge.getMinPoint().getY() <= position.getY()))
            {
                double x_value;
                double xp = getXP(edge, edge.getMaxPoint().getX() - edge.getMinPoint().getX(), position);

                if (edge.getMaxPoint().getX() >= edge.getMinPoint().getX())
                    x_value = edge.getMaxPoint().getX() - xp;
                else
                    x_value = edge.getMaxPoint().getX() + xp;

                double z_value;
                xp = getXP(edge, edge.getMaxPoint().getZ() - edge.getMinPoint().getZ(), position);

                if (edge.getMaxPoint().getZ() >= edge.getMinPoint().getZ())
                    z_value = edge.getMaxPoint().getZ() - xp;
                else
                    z_value = edge.getMaxPoint().getZ() + xp;

                Point3D point = new Point3D(x_value, position.getY(), z_value);

                if (edge.isInEdge(point))
                    points.add(new Point2D(point.getX(), point.getZ()));
            }
        }

        Polygon polygon = new Polygon(points.toArray(new Point2D[0]));
        for (Player player : Bukkit.getOnlinePlayers())
        {
            for (Point2D point_2D : polygon.getPoints())
            {
                player.spawnParticle(Particle.REDSTONE, new Location(player.getWorld(), point_2D.getX(),
                        position.getY(),
                        point_2D.getY()), 1, 0, 0, 0, 0, new Particle.DustOptions(Color.ORANGE, 0.1F));
            }
        }

        return super.isInside(position);
    }

    public double getXP(Edge edge, double bc, Vector position)
    {
        double ac = edge.getMaxPoint().getZ() - edge.getMinPoint().getZ();
        double ab = Math.sqrt(Math.pow(bc, 2) + Math.pow(ac, 2));
        double ap = edge.getMaxPoint().getZ() - position.getZ();
        double ax = ab * (ap / ac);
        return Math.sqrt(Math.pow(ax, 2) - Math.pow(ap, 2));
    }

    public void rotateX(double angle)
    {
        for (Point3D point : getPoints())
        {
            double sinTheta = Math.sin(angle);
            double cosTheta = Math.cos(angle);

            double y = point.getY() - getCenterY();
            double z = point.getZ() - getCenterZ();

            point.setY(y * cosTheta - z * sinTheta + getCenterY());
            point.setZ(z * cosTheta + y * sinTheta + getCenterZ());
        }

        sync();
    }

    public void rotateY(double angle)
    {
        for (Point3D point : getPoints())
        {
            double sinTheta = Math.sin(angle);
            double cosTheta = Math.cos(angle);

            double x = point.getX() - getCenterX();
            double z = point.getZ() - getCenterZ();

            point.setX(x * cosTheta - z * sinTheta + getCenterX());
            point.setZ(z * cosTheta + x * sinTheta + getCenterZ());
        }

        sync();
    }


    public void rotateZ(double angle)
    {
        for (Point3D point : getPoints())
        {
            double sinTheta = Math.sin(angle);
            double cosTheta = Math.cos(angle);

            double x = point.getX() - getCenterX();
            double y = point.getY() - getCenterY();

            point.setX(x * cosTheta - y * sinTheta + getCenterX());
            point.setY(y * cosTheta + x * sinTheta + getCenterY());
        }

        sync();
    }

    @Override
    public Vector getMax()
    {
        return max;
    }

    @Override
    public Vector getMin()
    {
        return min;
    }

    @Override
    public Edge[] getEdges()
    {
        return edges;
    }

    protected void sync()
    {
        edges = new Edge[]
                {
                        //Vertical Edge
                        new Edge(getPoints()[4], getPoints()[2]), //D->H
                        new Edge(getPoints()[7], getPoints()[3]), //C->G
                        new Edge(getPoints()[6], getPoints()[0]), //B->F
                        new Edge(getPoints()[5], getPoints()[1]), //A->E

                        //Horizontal Edge bottom
                        new Edge(getPoints()[4], getPoints()[7]), //D->C
                        new Edge(getPoints()[7], getPoints()[6]), //C->B
                        new Edge(getPoints()[6], getPoints()[5]), //B->A
                        new Edge(getPoints()[5], getPoints()[4]), //A->D

                        //Horizontal Edge top
                        new Edge(getPoints()[2], getPoints()[3]), //H->G
                        new Edge(getPoints()[3], getPoints()[0]), //G->F
                        new Edge(getPoints()[0], getPoints()[1]), //F->E
                        new Edge(getPoints()[1], getPoints()[2])  //E->H
                };

        for (Point3D point : getPoints())
        {
            if (point.getZ() < getMin().getZ() && point.getY() < getMin().getY() && point.getX() < getMin().getX())
                min = point.toVector();
            if (point.getZ() > getMax().getZ() && point.getY() > getMax().getY() && point.getX() > getMax().getX())
                max = point.toVector();
        }
    }
}
