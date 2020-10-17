package fr.bretzel.bcore.utils.maths;

import java.util.Arrays;

public class Polygon
{
    private final Point2D[] points;

    public Polygon(Point2D... points)
    {
        this.points = points;
    }

    public boolean isInPoint(Point2D ohter)
    {
        if (points.length == 0)
            return false;

        double minX = points[0].getX();
        double maxX = points[0].getX();
        double minY = points[0].getY();
        double maxY = points[0].getY();
        for (int i = 1; i < points.length; i++)
        {
            Point2D q = points[i];
            minX = Math.min(q.getX(), minX);
            maxX = Math.max(q.getX(), maxX);
            minY = Math.min(q.getY(), minY);
            maxY = Math.max(q.getY(), maxY);
        }

        if (ohter.getX() < minX || ohter.getX() > maxX || ohter.getY() < minY || ohter.getY() > maxY)
        {
            return false;
        }

        // https://wrf.ecse.rpi.edu/Research/Short_Notes/pnpoly.html
        boolean inside = false;

        for (Point2D point_2D : points)
        {
            if ((point_2D.getY() > ohter.getY()) != (point_2D.getY() > ohter.getY()) &&
                    ohter.getX() < (point_2D.getX() - point_2D.getX()) * (ohter.getY() - point_2D.getY()) / (point_2D.getY() - point_2D.getY()) + point_2D.getX())
            {
                inside = true;
                break;
            }
        }

        return inside;
    }

    public Point2D[] getPoints()
    {
        return points;
    }

    @Override
    public String toString()
    {
        return "Polygon{" +
                "points=" + Arrays.toString(points) +
                '}';
    }
}
