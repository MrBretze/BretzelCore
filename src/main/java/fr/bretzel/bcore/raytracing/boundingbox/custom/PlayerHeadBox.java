package fr.bretzel.bcore.raytracing.boundingbox.custom;

import fr.bretzel.bcore.raytracing.boundingbox.box.BlockDataPointingBox;
import org.bukkit.block.data.Rotatable;
import org.bukkit.util.Vector;

public class PlayerHeadBox extends BlockDataPointingBox<Rotatable>
{

    public PlayerHeadBox(Rotatable block_data)
    {
        super();

        rotateY(Math.toRadians(45));
        rotateX(Math.toRadians(45));
        rotateZ(Math.toRadians(45));
    }

    @Override
    public boolean isInside(Vector position)
    {
        super.isInside(position);
        return true;
    }
}
