package fr.bretzel.bcore.raytracing.boundingbox.custom;

import fr.bretzel.bcore.raytracing.boundingbox.box.BoundingBox;
import fr.bretzel.bcore.raytracing.boundingbox.box.MultipleBoundingBox;
import org.bukkit.block.data.type.Scaffolding;

public class ScaffoldingBox extends MultipleBoundingBox
{
    public ScaffoldingBox(Scaffolding block_data)
    {
        addBoundingBox(new BoundingBox(0.0, 0.125, 0.0, 0.125, 0.875, 0.125));
        addBoundingBox(new BoundingBox(0.0, 0.125, 0.875, 0.125, 0.875, 1.0));
        addBoundingBox(new BoundingBox(0.0, 0.875, 0.0, 1.0, 1.0, 1.0));
        addBoundingBox(new BoundingBox(0.875, 0.125, 0.875, 1.0, 0.875, 1.0));
        addBoundingBox(new BoundingBox(0.875, 0.125, 0.0, 1.0, 0.875, 0.125));

        if (block_data.isBottom())
            addBoundingBox(new BoundingBox(0.0, 0.0, 0.0, 1.0, 0.125, 1.0));

    }
}
