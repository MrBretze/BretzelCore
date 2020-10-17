package fr.bretzel.bcore.raytracing.boundingbox;

import fr.bretzel.bcore.raytracing.boundingbox.box.BoundingBox;
import fr.bretzel.bcore.raytracing.boundingbox.box.MultipleBoundingBox;
import fr.bretzel.bcore.raytracing.boundingbox.box.SimpleBlockBoundingBox;
import fr.bretzel.bcore.raytracing.boundingbox.custom.BrewingStandBox;
import fr.bretzel.bcore.raytracing.boundingbox.custom.PistonHeadBox;
import fr.bretzel.bcore.raytracing.boundingbox.custom.ScaffoldingBox;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.*;
import org.bukkit.util.Vector;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

public class BoundingBoxManager
{
    protected static final Hashtable<String, BoundingBox> cache = new Hashtable<>();

    protected static List<Material> has_block_data = new ArrayList<>();

    static
    {
        for (Material material : Material.values())
        {
            try
            {
                Field data = material.getClass().getField("data");
                Class<?> class_data = (Class<?>) data.get(material);

                if (!material.isLegacy() && class_data != null && BlockData.class.isAssignableFrom(class_data))
                    has_block_data.add(material);

            } catch (NoSuchFieldException | IllegalAccessException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param block the Block
     * @return the bounding box not translated
     */
    public static BoundingBox get(Block block)
    {
        BlockData block_data = block.getBlockData();
        Material material = block.getType();
        String data = block_data.getAsString();
        //System.out.println("CacheSize = " + cache.size());

        if (cache.containsKey(data))
            return cache.get(data);
        else
        {
            BoundingBox boundingBox = new SimpleBlockBoundingBox(block);

            if (material.isAir())
                boundingBox = new BoundingBox(0, 0, 0, 1, 1, 1)
                {
                    @Override
                    public boolean isInside(Vector position)
                    {
                        return false;
                    }
                };
            if (material == Material.PISTON_HEAD)
                boundingBox = new PistonHeadBox((PistonHead) block_data);
            else if (block_data instanceof Scaffolding)
                boundingBox = new ScaffoldingBox((Scaffolding) block_data);
            else if (block_data instanceof BrewingStand)
                boundingBox = new BrewingStandBox();
            else if (material == Material.LECTERN)
            {
                Lectern lectern = (Lectern) block_data;
                switch (lectern.getFacing())
                {
                    case NORTH:
                        return new MultipleBoundingBox(new BoundingBox(0.0, 0.0, 0.0, 1.0, 0.125, 1.0),
                                new BoundingBox(0.25, 0.125, 0.25, 0.75, 0.875, 0.75),
                                new BoundingBox(0.0, 0.625, 0.0625, 1.0, 0.875, 0.3125),
                                new BoundingBox(0.0, 0.75, 0.3125, 1.0, 1.0, 0.5625),
                                new BoundingBox(0.0, 0.875, 0.5625, 1.0, 1.125, 0.8125));
                    case SOUTH:
                        return new MultipleBoundingBox(
                                new BoundingBox(0.0, 0.0, 0.0, 1.0, 0.125, 1.0),
                                new BoundingBox(0.25, 0.125, 0.25, 0.75, 0.875, 0.75),
                                new BoundingBox(0.0, 0.625, 0.6875, 1.0, 0.875, 0.9375),
                                new BoundingBox(0.0, 0.75, 0.4375, 1.0, 1.0, 0.6875),
                                new BoundingBox(0.0, 0.875, 0.1875, 1.0, 1.125, 0.4375));
                    case EAST:
                        return new MultipleBoundingBox(
                                new BoundingBox(0.0, 0.0, 0.0, 1.0, 0.125, 1.0),
                                new BoundingBox(0.25, 0.125, 0.25, 0.75, 0.875, 0.75),
                                new BoundingBox(0.6875, 0.625, 0.0, 0.9375, 0.875, 1.0),
                                new BoundingBox(0.4375, 0.75, 0.0, 0.6875, 1.0, 1.0),
                                new BoundingBox(0.1875, 0.875, 0.0, 0.4375, 1.125, 1.0));
                    case WEST:
                        return new MultipleBoundingBox(
                                new BoundingBox(0.0, 0.0, 0.0, 1.0, 0.125, 1.0),
                                new BoundingBox(0.25, 0.125, 0.25, 0.75, 0.875, 0.75),
                                new BoundingBox(0.0625, 0.625, 0.0, 0.3125, 0.875, 1.0),
                                new BoundingBox(0.3125, 0.75, 0.0, 0.5625, 1.0, 1.0),
                                new BoundingBox(0.5625, 0.875, 0.0, 0.8125, 1.125, 1.0));
                }
            } else if (material == Material.HOPPER)
            {
                Hopper hopper = (Hopper) block_data;
                MultipleBoundingBox multipleBoundingBox = new MultipleBoundingBox(
                        new BoundingBox(0.0, 0.625, 0.0, 1.0, 1.0, 1.0),
                        new BoundingBox(0.25, 0.25, 0.25, 0.75, 0.625, 0.75));
                switch (hopper.getFacing())
                {
                    case NORTH:
                        return new MultipleBoundingBox(
                                new BoundingBox(0.375, 0.25, 0.0, 0.625, 0.5, 0.25),
                                multipleBoundingBox);
                    case SOUTH:
                        return new MultipleBoundingBox(
                                new BoundingBox(0.375, 0.25, 0.75, 0.625, 0.5, 1.0),
                                multipleBoundingBox);
                    case EAST:
                        return new MultipleBoundingBox(
                                new BoundingBox(0.75, 0.25, 0.375, 1.0, 0.5, 0.625),
                                multipleBoundingBox);
                    case WEST:
                        return new MultipleBoundingBox(
                                new BoundingBox(0.0, 0.25, 0.375, 0.25, 0.5, 0.625),
                                multipleBoundingBox);
                    case DOWN:
                        return new MultipleBoundingBox(
                                new BoundingBox(0.375, 0.0, 0.375, 0.625, 0.25, 0.625),
                                multipleBoundingBox);
                }
            } else if (material == Material.GRINDSTONE)
            {
                Grindstone grindstone = (Grindstone) block_data;
                switch (grindstone.getAttachedFace())
                {
                    case WALL:
                    {
                        switch (grindstone.getFacing())
                        {
                            case WEST:
                                return new MultipleBoundingBox(
                                        new BoundingBox(0.5625, 0.375, 0.75, 1.0, 0.625, 0.875),
                                        new BoundingBox(0.5625, 0.375, 0.125, 1.0, 0.625, 0.25),
                                        new BoundingBox(0.1875, 0.3125, 0.75, 0.5625, 0.6875, 0.875),
                                        new BoundingBox(0.1875, 0.3125, 0.125, 0.5625, 0.6875, 0.25),
                                        new BoundingBox(0.0, 0.125, 0.25, 0.75, 0.875, 0.75));
                            case SOUTH:
                                return new MultipleBoundingBox(
                                        new BoundingBox(0.25, 0.125, 0.25, 0.75, 0.875, 1.0),
                                        new BoundingBox(0.125, 0.3125, 0.4375, 0.25, 0.6875, 0.8125),
                                        new BoundingBox(0.75, 0.3125, 0.4375, 0.875, 0.6875, 0.8125),
                                        new BoundingBox(0.125, 0.375, 0.0, 0.25, 0.625, 0.4375),
                                        new BoundingBox(0.75, 0.375, 0.0, 0.875, 0.625, 0.4375));
                            case NORTH:
                                return new MultipleBoundingBox(
                                        new BoundingBox(0.75, 0.375, 0.5625, 0.875, 0.625, 1.0),
                                        new BoundingBox(0.125, 0.375, 0.5625, 0.25, 0.625, 1.0),
                                        new BoundingBox(0.75, 0.3125, 0.1875, 0.875, 0.6875, 0.5625),
                                        new BoundingBox(0.125, 0.3125, 0.1875, 0.25, 0.6875, 0.5625),
                                        new BoundingBox(0.25, 0.125, 0.0, 0.75, 0.875, 0.75));
                            case EAST:
                                return new MultipleBoundingBox(
                                        new BoundingBox(0.0, 0.375, 0.75, 0.4375, 0.625, 0.875),
                                        new BoundingBox(0.0, 0.375, 0.125, 0.4375, 0.625, 0.25),
                                        new BoundingBox(0.4375, 0.3125, 0.75, 0.8125, 0.6875, 0.875),
                                        new BoundingBox(0.4375, 0.3125, 0.125, 0.8125, 0.6875, 0.25),
                                        new BoundingBox(0.25, 0.125, 0.25, 1.0, 0.875, 0.75));
                        }
                    }
                    case FLOOR:
                    {
                        switch (grindstone.getFacing())
                        {
                            case EAST:
                            case WEST:
                                return new MultipleBoundingBox(
                                        new BoundingBox(0.375, 0.0, 0.75, 0.625, 0.4375, 0.875),
                                        new BoundingBox(0.375, 0.0, 0.125, 0.625, 0.4375, 0.25),
                                        new BoundingBox(0.3125, 0.4375, 0.75, 0.6875, 0.8125, 0.875),
                                        new BoundingBox(0.3125, 0.4375, 0.125, 0.6875, 0.8125, 0.25),
                                        new BoundingBox(0.125, 0.25, 0.25, 0.875, 1.0, 0.75));
                            case SOUTH:
                            case NORTH:
                                return new MultipleBoundingBox(
                                        new BoundingBox(0.75, 0.0, 0.375, 0.875, 0.4375, 0.625),
                                        new BoundingBox(0.125, 0.0, 0.375, 0.25, 0.4375, 0.625),
                                        new BoundingBox(0.75, 0.4375, 0.3125, 0.875, 0.8125, 0.6875),
                                        new BoundingBox(0.125, 0.4375, 0.3125, 0.25, 0.8125, 0.6875),
                                        new BoundingBox(0.25, 0.25, 0.125, 0.75, 1.0, 0.875));
                        }
                    }
                    case CEILING:
                    {
                        switch (grindstone.getFacing())
                        {
                            case SOUTH:
                            case NORTH:
                                return new MultipleBoundingBox(
                                        new BoundingBox(0.75, 0.5625, 0.375, 0.875, 1.0, 0.625),
                                        new BoundingBox(0.125, 0.5625, 0.375, 0.25, 1.0, 0.625),
                                        new BoundingBox(0.75, 0.1875, 0.3125, 0.875, 0.5625, 0.6875),
                                        new BoundingBox(0.125, 0.1875, 0.3125, 0.25, 0.5625, 0.6875),
                                        new BoundingBox(0.25, 0.0, 0.125, 0.75, 0.75, 0.875));
                            case EAST:
                            case WEST:
                                return new MultipleBoundingBox(
                                        new BoundingBox(0.375, 0.5625, 0.75, 0.625, 1.0, 0.875),
                                        new BoundingBox(0.375, 0.5625, 0.125, 0.625, 1.0, 0.25),
                                        new BoundingBox(0.3125, 0.1875, 0.75, 0.6875, 0.5625, 0.875),
                                        new BoundingBox(0.3125, 0.1875, 0.125, 0.6875, 0.5625, 0.25),
                                        new BoundingBox(0.125, 0.0, 0.25, 0.875, 0.75, 0.75));
                        }
                    }
                }
            } else if (material.name().endsWith("ANVIL"))
            {
                Directional directional = (Directional) block_data;
                switch (directional.getFacing())
                {
                    case SOUTH:
                    case NORTH:
                        return new MultipleBoundingBox(
                                new BoundingBox(0.125, 0.0, 0.125, 0.875, 0.25, 0.875),
                                new BoundingBox(0.25, 0.25, 0.1875, 0.75, 0.3125, 0.8125),
                                new BoundingBox(0.375, 0.3125, 0.25, 0.625, 0.625, 0.75),
                                new BoundingBox(0.1875, 0.625, 0.0, 0.8125, 1.0, 1.0));
                    case EAST:
                    case WEST:
                        return new MultipleBoundingBox(
                                new BoundingBox(0.125, 0.0, 0.125, 0.875, 0.25, 0.875),
                                new BoundingBox(0.1875, 0.25, 0.25, 0.8125, 0.3125, 0.75),
                                new BoundingBox(0.25, 0.3125, 0.375, 0.75, 0.625, 0.625),
                                new BoundingBox(0.0, 0.625, 0.1875, 1.0, 1.0, 0.8125));
                }
            } else if (block_data instanceof Stairs)
            {
                Stairs stairs = (Stairs) block_data;
                BoundingBox half = stairs.getHalf() == Bisected.Half.TOP ? new BoundingBox(0, 0.5, 0, 1, 1, 1) :
                        new BoundingBox(0, 0, 0, 1, 0.5, 1);

                switch (stairs.getShape())
                {
                    case STRAIGHT:
                        switch (stairs.getFacing())
                        {
                            case SOUTH:
                                return new MultipleBoundingBox(half, new BoundingBox(0, 0, 0.5, 1, 1, 1));
                            case NORTH:
                                return new MultipleBoundingBox(half, new BoundingBox(0, 0, 0, 1, 1, 0.5));
                            case EAST:
                                return new MultipleBoundingBox(half, new BoundingBox(0.5, 0, 0, 1, 1, 1));
                            case WEST:
                                return new MultipleBoundingBox(half, new BoundingBox(0, 0, 0, 0.5, 1, 1));
                        }
                        break;
                    case OUTER_LEFT:
                        switch (stairs.getFacing())
                        {
                            case SOUTH:
                                return new MultipleBoundingBox(half, new BoundingBox(0.5, 0, 0.5, 1, 1, 1));
                            case NORTH:
                                return new MultipleBoundingBox(half, new BoundingBox(0, 0, 0, 0.5, 1, 0.5));
                            case EAST:
                                return new MultipleBoundingBox(half, new BoundingBox(0.5, 0, 0, 1, 1, 0.5));
                            case WEST:
                                return new MultipleBoundingBox(half, new BoundingBox(0, 0, 0.5, 0.5, 1, 1));
                        }
                    case OUTER_RIGHT:
                        switch (stairs.getFacing())
                        {
                            case SOUTH:
                                return new MultipleBoundingBox(half, new BoundingBox(0, 0, 0.5, 0.5, 1, 1));
                            case NORTH:
                                return new MultipleBoundingBox(half, new BoundingBox(0.5, 0, 0, 1, 1, 0.5));
                            case EAST:
                                return new MultipleBoundingBox(half, new BoundingBox(0.5, 0, 1, 1, 1, 0.5));
                            case WEST:
                                return new MultipleBoundingBox(half, new BoundingBox(0, 0, 0, 0.5, 1, 0.5));
                        }
                        break;
                }

                return new MultipleBoundingBox(half, new BoundingBox());
            } else if (block_data instanceof Gate)
            {
                Gate gate = (Gate) block_data;
                if (gate.isOpen())
                {
                    boundingBox = new BoundingBox()
                    {
                        @Override
                        public boolean isInside(Vector position)
                        {
                            return false;
                        }
                    };
                }
            } else if (block_data instanceof Fence && material != Material.IRON_BARS && !(material.name().toLowerCase().endsWith("wall")))
            {
                Fence fence = (Fence) block_data;
                MultipleBoundingBox multipleBoundingBox = new MultipleBoundingBox();

                multipleBoundingBox.addBoundingBox(new BoundingBox(0.375, 0.0, 0.375, 0.625, 1.0, 0.625));

                for (BlockFace blockFace : fence.getFaces())
                {
                    switch (blockFace)
                    {
                        case EAST:
                            multipleBoundingBox
                                    .addBoundingBox(new BoundingBox(0.4375, 0.75, 0.4375, 1.0, 0.9375, 0.5625))
                                    .addBoundingBox(new BoundingBox(0.4375, 0.375, 0.4375, 1.0, 0.5625, 0.5625));
                            continue;
                        case WEST:
                            multipleBoundingBox
                                    .addBoundingBox(new BoundingBox(0.0, 0.75, 0.4375, 0.5625, 0.9375, 0.5625))
                                    .addBoundingBox(new BoundingBox(0.0, 0.375, 0.4375, 0.5625, 0.5625, 0.5625));
                            continue;
                        case NORTH:
                            multipleBoundingBox
                                    .addBoundingBox(new BoundingBox(0.4375, 0.75, 0.0, 0.5625, 0.9375, 0.5625))
                                    .addBoundingBox(new BoundingBox(0.4375, 0.375, 0.0, 0.5625, 0.5625, 0.5625));
                            continue;
                        case SOUTH:
                            multipleBoundingBox
                                    .addBoundingBox(new BoundingBox(0.4375, 0.75, 0.4375, 0.5625, 0.9375, 1.0))
                                    .addBoundingBox(new BoundingBox(0.4375, 0.375, 0.4375, 0.5625, 0.5625, 1.0));
                    }
                }

                boundingBox = multipleBoundingBox;
            }

            cache.put(data, boundingBox);
            return boundingBox;
        }
    }
}
