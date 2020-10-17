package fr.bretzel.bcore.utils.maths;

public class Point2D
{
    private double x = 0, y = 0;

    public Point2D(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    @Override
    public String toString()
    {
        return "Point2D[" +
                "x=" + x +
                ", y=" + y +
                ']';
    }
}
