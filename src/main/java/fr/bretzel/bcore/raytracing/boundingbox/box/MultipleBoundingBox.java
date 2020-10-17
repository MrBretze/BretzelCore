package fr.bretzel.bcore.raytracing.boundingbox.box;

import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.stream.Stream;

public class MultipleBoundingBox extends BoundingBox
{
    private BoundingBox[] boundingBoxes;

    private Vector min;
    private Vector max;

    public MultipleBoundingBox()
    {
        super();
        clearAll();
    }

    public MultipleBoundingBox(BoundingBox... boundingBoxes)
    {
        super();
        this.boundingBoxes = boundingBoxes;
        refreshMinAndMax();
    }

    public MultipleBoundingBox addBoundingBox(BoundingBox boundingBox)
    {
        BoundingBox[] bdbox = new BoundingBox[]{boundingBox};

        BoundingBox[] array = new BoundingBox[boundingBoxes.length + bdbox.length];

        System.arraycopy(boundingBoxes, 0, array, 0, boundingBoxes.length);
        System.arraycopy(bdbox, 0, array, boundingBoxes.length, bdbox.length);

        this.boundingBoxes = array;

        refreshMinAndMax();
        return this;
    }

    @Override
    public boolean isInside(Vector position)
    {
        return Stream.of(getBoundingBoxes()).anyMatch(boundingBox -> boundingBox.isInside(position));
    }

    public int indexOf(BoundingBox boundingBox)
    {
        int index = 0;

        for (; index < boundingBoxes.length; index++)
        {
            if (boundingBox.equals(boundingBoxes[index]))
                return index;
        }
        return index;
    }

    public boolean has(int index)
    {
        return boundingBoxes.length < index;
    }

    public BoundingBox get(int index)
    {
        return boundingBoxes[index];
    }

    public BoundingBox[] getBoundingBoxes()
    {
        return boundingBoxes;
    }

    @Override
    public BoundingBox translate(Block block)
    {
        Stream.of(getBoundingBoxes()).forEach(boundingBox -> boundingBox.translate(block));
        return this;
    }

    public void clearAll()
    {
        this.boundingBoxes = new BoundingBox[0];
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

    /**
     * set the new min vector value and max vector value
     */
    public void refreshMinAndMax()
    {
        double minX = -1, minY = -1, minZ = -1;
        double maxX = -1, maxY = -1, maxZ = -1;

        for (BoundingBox boundingBox : getBoundingBoxes())
        {
            if (minX == -1 || minX > boundingBox.getMin().getX())
                minX = boundingBox.getMin().getX();

            if (minY == -1 || minY > boundingBox.getMin().getY())
                minY = boundingBox.getMin().getY();

            if (minZ == -1 || minZ > boundingBox.getMin().getZ())
                minZ = boundingBox.getMin().getZ();

            if (maxX == -1 || maxX > boundingBox.getMax().getX())
                maxX = boundingBox.getMax().getX();

            if (maxY == -1 || maxY > boundingBox.getMax().getY())
                maxY = boundingBox.getMax().getY();

            if (maxZ == -1 || maxZ > boundingBox.getMax().getZ())
                maxZ = boundingBox.getMax().getZ();
        }

        this.max = new Vector(maxX, maxY, maxZ);
        this.min = new Vector(minX, minY, minZ);
    }
}
