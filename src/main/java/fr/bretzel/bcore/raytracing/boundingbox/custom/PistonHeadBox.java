package fr.bretzel.bcore.raytracing.boundingbox.custom;

import fr.bretzel.bcore.raytracing.boundingbox.box.BlockDataMultipleBoundingBox;
import fr.bretzel.bcore.raytracing.boundingbox.box.BoundingBox;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.PistonHead;

public class PistonHeadBox extends BlockDataMultipleBoundingBox<PistonHead>
{
    public PistonHeadBox(PistonHead block_data)
    {
        setBlockData(block_data);
    }

    @Override
    public BoundingBox translate(Block block)
    {
        BlockData blockData = block.getBlockData();

        if (getBlockData().matches(blockData))
        {
            return this;
        } else if (blockData instanceof PistonHead)
        {
            setBlockData((PistonHead) blockData);

            clearAll();

            switch (getBlockData().getFacing())
            {
                case NORTH:
                    addBoundingBox(new BoundingBox(0.0, 0.0, 0.0, 1.0, 1.0, 0.25));
                    addBoundingBox(new BoundingBox(0.375, 0.375, 0.25, 0.625, 0.625, 1.25));
                    break;
                case SOUTH:
                    addBoundingBox(new BoundingBox(0.0, 0.0, 0.75, 1.0, 1.0, 1.0));
                    addBoundingBox(new BoundingBox(0.375, 0.375, -0.25, 0.625, 0.625, 0.75));
                    break;
                case EAST:
                    addBoundingBox(new BoundingBox(0.75, 0.0, 0.0, 1.0, 1.0, 1.0));
                    addBoundingBox(new BoundingBox(-0.25, 0.375, 0.375, 0.75, 0.625, 0.625));
                    break;
                case WEST:
                    addBoundingBox(new BoundingBox(0.0, 0.0, 0.0, 0.25, 1.0, 1.0));
                    addBoundingBox(new BoundingBox(0.25, 0.375, 0.375, 1.25, 0.625, 0.625));
                    break;
                case UP:
                    addBoundingBox(new BoundingBox(0.0, 0.75, 0.0, 1.0, 1.0, 1.0));
                    addBoundingBox(new BoundingBox(0.375, -0.25, 0.375, 0.625, 0.75, 0.625));
                    break;
                case DOWN:
                    addBoundingBox(new BoundingBox(0.0, 0.0, 0.0, 1.0, 0.25, 1.0));
                    addBoundingBox(new BoundingBox(0.375, 0.25, 0.375, 0.625, 1.25, 0.625));
                    break;
            }
        }

        return super.translate(block);
    }
}
