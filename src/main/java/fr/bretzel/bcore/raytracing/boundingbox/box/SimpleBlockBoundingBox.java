package fr.bretzel.bcore.raytracing.boundingbox.box;

import org.bukkit.block.Block;
import org.bukkit.util.Vector;

public class SimpleBlockBoundingBox extends BoundingBox
{
    private final Block block;
    private final org.bukkit.util.BoundingBox bounding_box;

    public SimpleBlockBoundingBox(Block block)
    {
        bounding_box = block.getBoundingBox();
        this.block = block;
    }

    public org.bukkit.util.BoundingBox getBoundingBox()
    {
        return bounding_box;
    }

    @Override
    public Vector getMax()
    {
        return bounding_box.getMax().subtract(new Vector(block.getX(), block.getY(), block.getZ()));
    }

    @Override
    public Vector getMin()
    {
        return bounding_box.getMin().subtract(new Vector(block.getX(), block.getY(), block.getZ()));
    }

    public Block getBlock()
    {
        return block;
    }
}
