package fr.bretzel.bcore.raytracing.boundingbox.box;

import fr.bretzel.bcore.utils.maths.Edge;
import fr.bretzel.bcore.utils.maths.Point3D;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.Objects;

public class BoundingBox
{
    //min and max points of hit box
    private final Vector max;
    private final Vector min;

    private Point3D block_pos;

    public BoundingBox()
    {
        this(new Vector(0, 0, 0), new Vector(1, 1, 1));
    }

    public BoundingBox(Vector min, Vector max)
    {
        this.max = max;
        this.min = min;
    }

    public BoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
    {
        this(new Vector(minX, minY, minZ), new Vector(maxX, maxY, maxZ));
    }

    public double getHeight()
    {
        return getTranslatedMax().getY() - getTranslatedMin().getY();
    }

    public double getWidthX()
    {
        return getTranslatedMax().getX() - getTranslatedMin().getX();
    }

    public double getWidthZ()
    {
        return getTranslatedMax().getZ() - getTranslatedMin().getZ();
    }

    public double getVolume()
    {
        return this.getHeight() * this.getWidthX() * this.getWidthZ();
    }

    public double getCenterX()
    {
        return getTranslatedMin().getX() + this.getWidthX() * 0.5D;
    }

    public double getCenterY()
    {
        return getTranslatedMin().getY() + this.getHeight() * 0.5D;
    }

    public double getCenterZ()
    {
        return getTranslatedMin().getZ() + this.getWidthZ() * 0.5D;
    }

    public Vector getMax()
    {
        return max;
    }

    public Vector getMin()
    {
        return min;
    }

    public Vector getCenter()
    {
        return new Vector(this.getCenterX(), this.getCenterY(), this.getCenterZ());
    }

    public boolean isInside(Vector position)
    {
        return position.getX() >= getTranslatedMin().getX() && position.getX() <= getTranslatedMax().getX() && position.getY() >= getTranslatedMin().getY() && position.getY() <= getTranslatedMax().getY() && position.getZ() >= getTranslatedMin().getZ() && position.getZ() <= getTranslatedMax().getZ();
    }

    public Edge[] getEdges()
    {
        return new Edge[]
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
    }

    public Point3D[] getPoints()
    {
        return new Point3D[]
                {
                        //Top Point
                        new Point3D(getTranslatedMax().getX(), getTranslatedMax().getY(), getTranslatedMax().getZ()),//F Point   Index 0
                        new Point3D(getTranslatedMin().getX(), getTranslatedMax().getY(), getTranslatedMax().getZ()),//E Point   Index 1
                        new Point3D(getTranslatedMin().getX(), getTranslatedMax().getY(), getTranslatedMin().getZ()),//H Point   Index 2
                        new Point3D(getTranslatedMax().getX(), getTranslatedMax().getY(), getTranslatedMin().getZ()),//G Point   Index 3

                        //Bottom Point
                        new Point3D(getTranslatedMin().getX(), getTranslatedMin().getY(), getTranslatedMin().getZ()),//D Point    Index 4
                        new Point3D(getTranslatedMin().getX(), getTranslatedMin().getY(), getTranslatedMax().getZ()),//A Point    Index 5
                        new Point3D(getTranslatedMax().getX(), getTranslatedMin().getY(), getTranslatedMax().getZ()),//B Point    Index 6
                        new Point3D(getTranslatedMax().getX(), getTranslatedMin().getY(), getTranslatedMin().getZ()) //C Point    Index 7
                };
    }

    public BoundingBox translate(Point3D point3D, World world)
    {
        return translate(point3D.toLocation(world));
    }

    public BoundingBox translate(Location location)
    {
        return translate(location.getBlock());
    }

    public BoundingBox translate(Block block)
    {
        this.block_pos = new Point3D(block.getX(), block.getY(), block.getZ());
        return this;
    }

    public Point3D getBlockPos()
    {
        return block_pos == null ? new Point3D(0, 0, 0) : block_pos;
    }

    public Point3D getTranslatedMin()
    {
        return new Point3D(getBlockPos().getX() + getMin().getX(), getBlockPos().getY() + getMin().getY(), getBlockPos().getZ() + getMin().getZ());
    }

    public Point3D getTranslatedMax()
    {
        return new Point3D(getBlockPos().getX() + getMax().getX(), getBlockPos().getY() + getMax().getY(), getBlockPos().getZ() + getMax().getZ());
    }

    @Override
    public String toString()
    {
        return "BoundingBox{" +
                "max=" + max +
                ", min=" + min +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof BoundingBox)) return false;

        BoundingBox that = (BoundingBox) o;

        if (getMax() != null ? !getMax().equals(that.getMax()) : that.getMax() != null) return false;
        if (getMin() != null ? !getMin().equals(that.getMin()) : that.getMin() != null) return false;
        return Objects.equals(block_pos, that.block_pos);
    }

    @Override
    public int hashCode()
    {
        int result = getMax() != null ? getMax().hashCode() : 0;
        result = 31 * result + (getMin() != null ? getMin().hashCode() : 0);
        result = 31 * result + (block_pos != null ? block_pos.hashCode() : 0);
        return result;
    }
}
