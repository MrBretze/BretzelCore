package fr.bretzel.bcore.utils.boundingbox;

import org.bukkit.block.Block;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.List;

public class MultipleBoundingBox extends BoundingBox
{
    private final List<BoundingBox> boundingBoxes;

    protected MultipleBoundingBox(Block block, BoundingBox... boundingBoxes)
    {
        super(block);

        this.boundingBoxes = Arrays.asList(boundingBoxes);
    }

    @Override
    public boolean isInside(Vector position)
    {
        return boundingBoxes.stream().filter(boundingBox -> boundingBox.isInside(position)).findFirst().orElse(new BoundingBox(null, null)
        {
            @Override
            public boolean isInside(Vector position)
            {
                return false;
            }
        }).isInside(position);
    }

    public List<BoundingBox> getBoundingBoxes()
    {
        return boundingBoxes;
    }
}
