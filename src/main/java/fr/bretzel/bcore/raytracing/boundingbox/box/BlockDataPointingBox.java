package fr.bretzel.bcore.raytracing.boundingbox.box;

import org.bukkit.block.data.BlockData;

public abstract class BlockDataPointingBox<T extends BlockData> extends PointingBox implements BlockDataBox<T>
{
    private T block_data;

    @Override
    public T getBlockData()
    {
        return block_data;
    }

    @Override
    public void setBlockData(T block_data)
    {
        this.block_data = block_data;
    }
}
