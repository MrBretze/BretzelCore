package fr.bretzel.bcore.raytracing.boundingbox.box;

import org.bukkit.block.data.BlockData;

public interface BlockDataBox<T extends BlockData>
{
    T getBlockData();

    void setBlockData(T block_data);
}
