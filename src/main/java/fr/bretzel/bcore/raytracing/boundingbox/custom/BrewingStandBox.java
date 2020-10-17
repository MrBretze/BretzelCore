package fr.bretzel.bcore.raytracing.boundingbox.custom;

import fr.bretzel.bcore.raytracing.boundingbox.box.BoundingBox;
import fr.bretzel.bcore.raytracing.boundingbox.box.MultipleBoundingBox;

public class BrewingStandBox extends MultipleBoundingBox
{
    public BrewingStandBox()
    {
        addBoundingBox(new BoundingBox(0.4375, 0.0, 0.4375, 0.5625, 0.875, 0.5625));
        addBoundingBox(new BoundingBox(0.0625, 0.0, 0.0625, 0.9375, 0.125, 0.9375));
    }
}
