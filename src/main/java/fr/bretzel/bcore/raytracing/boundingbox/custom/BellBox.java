package fr.bretzel.bcore.raytracing.boundingbox.custom;

import fr.bretzel.bcore.raytracing.boundingbox.box.BoundingBox;
import fr.bretzel.bcore.raytracing.boundingbox.box.MultipleBoundingBox;
import org.bukkit.block.data.type.Bell;

public class BellBox extends MultipleBoundingBox
{
    public BellBox(Bell bell)
    {
        super();
        addBoundingBox(new BoundingBox(0.3125, 0.375, 0.3125, 0.6875, 0.8125, 0.6875));
        addBoundingBox(new BoundingBox(0.25, 0.25, 0.25, 0.75, 0.375, 0.75));
    }
}
