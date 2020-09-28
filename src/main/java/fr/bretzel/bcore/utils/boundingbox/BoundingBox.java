package fr.bretzel.bcore.utils.boundingbox;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.*;
import org.bukkit.util.Vector;

public class BoundingBox
{

    //min and max points of hit box
    Vector max;
    Vector min;

    protected BoundingBox(Block block)
    {
        org.bukkit.util.BoundingBox boundingBox = block.getBoundingBox();
        max = boundingBox.getMax();
        min = boundingBox.getMin();
    }

    protected BoundingBox(Vector min, Vector max)
    {
        this.max = max;
        this.min = min;
    }

    protected BoundingBox(Block b, double minX, double minY, double minZ, double maxX, double maxY, double maxZ)
    {
        this(new Vector(b.getX() + minX, b.getY() + minY, b.getZ() + minZ), new Vector(b.getX() + maxX, b.getY() + maxY, b.getZ() + maxZ));
    }

    public static BoundingBox getBoundingBox(Block block)
    {
        BlockData blockData = block.getBlockData();
        Material material = block.getType();

        if (material == Material.SCAFFOLDING)
        {
            Scaffolding scaffolding = (Scaffolding) blockData;
            if (scaffolding.isBottom())
            {
                return new MultipleBoundingBox(block,
                        new BoundingBox(block, 0.0, 0.125, 0.0, 0.125, 0.875, 0.125),
                        new BoundingBox(block, 0.0, 0.125, 0.875, 0.125, 0.875, 1.0),
                        new BoundingBox(block, 0.875, 0.125, 0.875, 1.0, 0.875, 1.0),
                        new BoundingBox(block, 0.875, 0.125, 0.0, 1.0, 0.875, 0.125),
                        new BoundingBox(block, 0.0, 0.875, 0.0, 1.0, 1.0, 1.0),
                        new BoundingBox(block, 0.0, 0.0, 0.0, 1.0, 0.125, 1.0));
            } else
            {
                return new MultipleBoundingBox(block,
                        new BoundingBox(block, 0.0, 0.875, 0.0, 1.0, 1.0, 1.0),
                        new BoundingBox(block, 0.0, 0.0, 0.0, 0.125, 0.875, 0.125),
                        new BoundingBox(block, 0.0, 0.0, 0.875, 0.125, 0.875, 1.0),
                        new BoundingBox(block, 0.875, 0.0, 0.875, 1.0, 0.875, 1.0),
                        new BoundingBox(block, 0.875, 0.0, 0.0, 1.0, 0.875, 0.125));
            }
        } else if (material == Material.BREWING_STAND)
        {
            return new MultipleBoundingBox(block,
                    new BoundingBox(block, 0.4375, 0.0, 0.4375, 0.5625, 0.875, 0.5625),
                    new BoundingBox(block, 0.0625, 0.0, 0.0625, 0.9375, 0.125, 0.9375));
        } else if (material == Material.PISTON_HEAD)
        {
            PistonHead pistonHead = (PistonHead) blockData;
            switch (pistonHead.getFacing())
            {
                case NORTH:
                    return new MultipleBoundingBox(block,
                            new BoundingBox(block, 0.0, 0.0, 0.0, 1.0, 1.0, 0.25),
                            new BoundingBox(block, 0.375, 0.375, 0.25, 0.625, 0.625, 1.25));
                case SOUTH:
                    return new MultipleBoundingBox(block,
                            new BoundingBox(block, 0.0, 0.0, 0.75, 1.0, 1.0, 1.0),
                            new BoundingBox(block, 0.375, 0.375, -0.25, 0.625, 0.625, 0.75));
                case EAST:
                    return new MultipleBoundingBox(block,
                            new BoundingBox(block, 0.75, 0.0, 0.0, 1.0, 1.0, 1.0),
                            new BoundingBox(block, -0.25, 0.375, 0.375, 0.75, 0.625, 0.625));
                case WEST:
                    return new MultipleBoundingBox(block,
                            new BoundingBox(block, 0.0, 0.0, 0.0, 0.25, 1.0, 1.0),
                            new BoundingBox(block, 0.25, 0.375, 0.375, 1.25, 0.625, 0.625));
                case UP:
                    return new MultipleBoundingBox(block,
                            new BoundingBox(block, 0.0, 0.75, 0.0, 1.0, 1.0, 1.0),
                            new BoundingBox(block, 0.375, -0.25, 0.375, 0.625, 0.75, 0.625));
                case DOWN:
                    return new MultipleBoundingBox(block,
                            new BoundingBox(block, 0.0, 0.0, 0.0, 1.0, 0.25, 1.0),
                            new BoundingBox(block, 0.375, 0.25, 0.375, 0.625, 1.25, 0.625));
            }
        } else if (material == Material.LECTERN)
        {
            Lectern lectern = (Lectern) blockData;
            switch (lectern.getFacing())
            {
                case NORTH:
                    return new MultipleBoundingBox(block,
                            new BoundingBox(block, 0.0, 0.0, 0.0, 1.0, 0.125, 1.0),
                            new BoundingBox(block, 0.25, 0.125, 0.25, 0.75, 0.875, 0.75),
                            new BoundingBox(block, 0.0, 0.625, 0.0625, 1.0, 0.875, 0.3125),
                            new BoundingBox(block, 0.0, 0.75, 0.3125, 1.0, 1.0, 0.5625),
                            new BoundingBox(block, 0.0, 0.875, 0.5625, 1.0, 1.125, 0.8125));
                case SOUTH:
                    return new MultipleBoundingBox(block,
                            new BoundingBox(block, 0.0, 0.0, 0.0, 1.0, 0.125, 1.0),
                            new BoundingBox(block, 0.25, 0.125, 0.25, 0.75, 0.875, 0.75),
                            new BoundingBox(block, 0.0, 0.625, 0.6875, 1.0, 0.875, 0.9375),
                            new BoundingBox(block, 0.0, 0.75, 0.4375, 1.0, 1.0, 0.6875),
                            new BoundingBox(block, 0.0, 0.875, 0.1875, 1.0, 1.125, 0.4375));
                case EAST:
                    return new MultipleBoundingBox(block,
                            new BoundingBox(block, 0.0, 0.0, 0.0, 1.0, 0.125, 1.0),
                            new BoundingBox(block, 0.25, 0.125, 0.25, 0.75, 0.875, 0.75),
                            new BoundingBox(block, 0.6875, 0.625, 0.0, 0.9375, 0.875, 1.0),
                            new BoundingBox(block, 0.4375, 0.75, 0.0, 0.6875, 1.0, 1.0),
                            new BoundingBox(block, 0.1875, 0.875, 0.0, 0.4375, 1.125, 1.0));
                case WEST:
                    return new MultipleBoundingBox(block,
                            new BoundingBox(block, 0.0, 0.0, 0.0, 1.0, 0.125, 1.0),
                            new BoundingBox(block, 0.25, 0.125, 0.25, 0.75, 0.875, 0.75),
                            new BoundingBox(block, 0.0625, 0.625, 0.0, 0.3125, 0.875, 1.0),
                            new BoundingBox(block, 0.3125, 0.75, 0.0, 0.5625, 1.0, 1.0),
                            new BoundingBox(block, 0.5625, 0.875, 0.0, 0.8125, 1.125, 1.0));
            }
        } else if (material == Material.HOPPER)
        {
            Hopper hopper = (Hopper) blockData;
            MultipleBoundingBox multipleBoundingBox = new MultipleBoundingBox(block,
                    new BoundingBox(block, 0.0, 0.625, 0.0, 1.0, 1.0, 1.0),
                    new BoundingBox(block, 0.25, 0.25, 0.25, 0.75, 0.625, 0.75));
            switch (hopper.getFacing())
            {
                case NORTH:
                    return new MultipleBoundingBox(block,
                            new BoundingBox(block, 0.375, 0.25, 0.0, 0.625, 0.5, 0.25),
                            multipleBoundingBox);
                case SOUTH:
                    return new MultipleBoundingBox(block,
                            new BoundingBox(block, 0.375, 0.25, 0.75, 0.625, 0.5, 1.0),
                            multipleBoundingBox);
                case EAST:
                    return new MultipleBoundingBox(block,
                            new BoundingBox(block, 0.75, 0.25, 0.375, 1.0, 0.5, 0.625),
                            multipleBoundingBox);
                case WEST:
                    return new MultipleBoundingBox(block,
                            new BoundingBox(block, 0.0, 0.25, 0.375, 0.25, 0.5, 0.625),
                            multipleBoundingBox);
                case DOWN:
                    return new MultipleBoundingBox(block,
                            new BoundingBox(block, 0.375, 0.0, 0.375, 0.625, 0.25, 0.625),
                            multipleBoundingBox);

            }
        } else if (material == Material.GRINDSTONE)
        {
            Grindstone grindstone = (Grindstone) blockData;
            switch (grindstone.getAttachedFace())
            {
                case WALL:
                {
                    switch (grindstone.getFacing())
                    {
                        case WEST:
                            return new MultipleBoundingBox(block,
                                    new BoundingBox(block, 0.5625, 0.375, 0.75, 1.0, 0.625, 0.875),
                                    new BoundingBox(block, 0.5625, 0.375, 0.125, 1.0, 0.625, 0.25),
                                    new BoundingBox(block, 0.1875, 0.3125, 0.75, 0.5625, 0.6875, 0.875),
                                    new BoundingBox(block, 0.1875, 0.3125, 0.125, 0.5625, 0.6875, 0.25),
                                    new BoundingBox(block, 0.0, 0.125, 0.25, 0.75, 0.875, 0.75));
                        case SOUTH:
                            return new MultipleBoundingBox(block,
                                    new BoundingBox(block, 0.25, 0.125, 0.25, 0.75, 0.875, 1.0),
                                    new BoundingBox(block, 0.125, 0.3125, 0.4375, 0.25, 0.6875, 0.8125),
                                    new BoundingBox(block, 0.75, 0.3125, 0.4375, 0.875, 0.6875, 0.8125),
                                    new BoundingBox(block, 0.125, 0.375, 0.0, 0.25, 0.625, 0.4375),
                                    new BoundingBox(block, 0.75, 0.375, 0.0, 0.875, 0.625, 0.4375));
                        case NORTH:
                            return new MultipleBoundingBox(block,
                                    new BoundingBox(block, 0.75, 0.375, 0.5625, 0.875, 0.625, 1.0),
                                    new BoundingBox(block, 0.125, 0.375, 0.5625, 0.25, 0.625, 1.0),
                                    new BoundingBox(block, 0.75, 0.3125, 0.1875, 0.875, 0.6875, 0.5625),
                                    new BoundingBox(block, 0.125, 0.3125, 0.1875, 0.25, 0.6875, 0.5625),
                                    new BoundingBox(block, 0.25, 0.125, 0.0, 0.75, 0.875, 0.75));
                        case EAST:
                            return new MultipleBoundingBox(block,
                                    new BoundingBox(block, 0.0, 0.375, 0.75, 0.4375, 0.625, 0.875),
                                    new BoundingBox(block, 0.0, 0.375, 0.125, 0.4375, 0.625, 0.25),
                                    new BoundingBox(block, 0.4375, 0.3125, 0.75, 0.8125, 0.6875, 0.875),
                                    new BoundingBox(block, 0.4375, 0.3125, 0.125, 0.8125, 0.6875, 0.25),
                                    new BoundingBox(block, 0.25, 0.125, 0.25, 1.0, 0.875, 0.75));
                    }
                }
                case FLOOR:
                {
                    switch (grindstone.getFacing())
                    {
                        case EAST:
                        case WEST:
                            return new MultipleBoundingBox(block,
                                    new BoundingBox(block, 0.375, 0.0, 0.75, 0.625, 0.4375, 0.875),
                                    new BoundingBox(block, 0.375, 0.0, 0.125, 0.625, 0.4375, 0.25),
                                    new BoundingBox(block, 0.3125, 0.4375, 0.75, 0.6875, 0.8125, 0.875),
                                    new BoundingBox(block, 0.3125, 0.4375, 0.125, 0.6875, 0.8125, 0.25),
                                    new BoundingBox(block, 0.125, 0.25, 0.25, 0.875, 1.0, 0.75));
                        case SOUTH:
                        case NORTH:
                            return new MultipleBoundingBox(block,
                                    new BoundingBox(block, 0.75, 0.0, 0.375, 0.875, 0.4375, 0.625),
                                    new BoundingBox(block, 0.125, 0.0, 0.375, 0.25, 0.4375, 0.625),
                                    new BoundingBox(block, 0.75, 0.4375, 0.3125, 0.875, 0.8125, 0.6875),
                                    new BoundingBox(block, 0.125, 0.4375, 0.3125, 0.25, 0.8125, 0.6875),
                                    new BoundingBox(block, 0.25, 0.25, 0.125, 0.75, 1.0, 0.875));
                    }
                }
                case CEILING:
                {
                    switch (grindstone.getFacing())
                    {
                        case SOUTH:
                        case NORTH:
                            return new MultipleBoundingBox(block,
                                    new BoundingBox(block, 0.75, 0.5625, 0.375, 0.875, 1.0, 0.625),
                                    new BoundingBox(block, 0.125, 0.5625, 0.375, 0.25, 1.0, 0.625),
                                    new BoundingBox(block, 0.75, 0.1875, 0.3125, 0.875, 0.5625, 0.6875),
                                    new BoundingBox(block, 0.125, 0.1875, 0.3125, 0.25, 0.5625, 0.6875),
                                    new BoundingBox(block, 0.25, 0.0, 0.125, 0.75, 0.75, 0.875));
                        case EAST:
                        case WEST:
                            return new MultipleBoundingBox(block,
                                    new BoundingBox(block, 0.375, 0.5625, 0.75, 0.625, 1.0, 0.875),
                                    new BoundingBox(block, 0.375, 0.5625, 0.125, 0.625, 1.0, 0.25),
                                    new BoundingBox(block, 0.3125, 0.1875, 0.75, 0.6875, 0.5625, 0.875),
                                    new BoundingBox(block, 0.3125, 0.1875, 0.125, 0.6875, 0.5625, 0.25),
                                    new BoundingBox(block, 0.125, 0.0, 0.25, 0.875, 0.75, 0.75));
                    }
                }
            }
        } else if (material.name().endsWith("ANVIL"))
        {
            Directional directional = (Directional) blockData;
            switch (directional.getFacing())
            {
                case SOUTH:
                case NORTH:
                    return new MultipleBoundingBox(block,
                            new BoundingBox(block, 0.125, 0.0, 0.125, 0.875, 0.25, 0.875),
                            new BoundingBox(block, 0.25, 0.25, 0.1875, 0.75, 0.3125, 0.8125),
                            new BoundingBox(block, 0.375, 0.3125, 0.25, 0.625, 0.625, 0.75),
                            new BoundingBox(block, 0.1875, 0.625, 0.0, 0.8125, 1.0, 1.0));
                case EAST:
                case WEST:
                    return new MultipleBoundingBox(block,
                            new BoundingBox(block, 0.125, 0.0, 0.125, 0.875, 0.25, 0.875),
                            new BoundingBox(block, 0.1875, 0.25, 0.25, 0.8125, 0.3125, 0.75),
                            new BoundingBox(block, 0.25, 0.3125, 0.375, 0.75, 0.625, 0.625),
                            new BoundingBox(block, 0.0, 0.625, 0.1875, 1.0, 1.0, 0.8125));
            }
        }

        if (blockData instanceof Stairs)
        {
            Stairs stairs = (Stairs) blockData;
            BoundingBox half = stairs.getHalf() == Bisected.Half.TOP ? new BoundingBox(block, 0, 0.5, 0, 1, 1, 1) :
                    new BoundingBox(block, 0, 0, 0, 1, 0.5, 1);

            switch (stairs.getShape())
            {
                case STRAIGHT:
                    switch (stairs.getFacing())
                    {
                        case SOUTH:
                            return new MultipleBoundingBox(block, half, new BoundingBox(block, 0, 0, 0.5, 1, 1, 1));
                        case NORTH:
                            return new MultipleBoundingBox(block, half, new BoundingBox(block, 0, 0, 0, 1, 1, 0.5));
                        case EAST:
                            return new MultipleBoundingBox(block, half, new BoundingBox(block, 0.5, 0, 0, 1, 1, 1));
                        case WEST:
                            return new MultipleBoundingBox(block, half, new BoundingBox(block, 0, 0, 0, 0.5, 1, 1));
                    }
                case OUTER_LEFT:
                    switch (stairs.getFacing())
                    {
                        case SOUTH:
                            return new MultipleBoundingBox(block, half, new BoundingBox(block, 0.5, 0, 0.5, 1, 1, 1));
                        case NORTH:
                            return new MultipleBoundingBox(block, half, new BoundingBox(block, 0, 0, 0, 0.5, 1, 0.5));
                        case EAST:
                            return new MultipleBoundingBox(block, half, new BoundingBox(block, 0.5, 0, 0, 1, 1, 0.5));
                        case WEST:
                            return new MultipleBoundingBox(block, half, new BoundingBox(block, 0, 0, 0.5, 0.5, 1, 1));
                    }
                case OUTER_RIGHT:
                    switch (stairs.getFacing())
                    {
                        case SOUTH:
                            return new MultipleBoundingBox(block, half, new BoundingBox(block, 0, 0, 0.5, 0.5, 1, 1));
                        case NORTH:
                            return new MultipleBoundingBox(block, half, new BoundingBox(block, 0.5, 0, 0, 1, 1, 0.5));
                        case EAST:
                            return new MultipleBoundingBox(block, half, new BoundingBox(block, 0.5, 0, 1, 1, 1, 0.5));
                        case WEST:
                            return new MultipleBoundingBox(block, half, new BoundingBox(block, 0, 0, 0, 0.5, 1, 0.5));
                    }
            }

            return new MultipleBoundingBox(block, half, new BoundingBox(block));
        } else if (blockData instanceof Gate)
        {
            Gate gate = (Gate) blockData;
            if (gate.isOpen())
            {
                org.bukkit.util.BoundingBox boundingBox = block.getBoundingBox();
                return new BoundingBox(boundingBox.getMin(), boundingBox.getMax())
                {
                    @Override
                    public boolean isInside(Vector position)
                    {
                        return false;
                    }
                };
            }
        }

        return new BoundingBox(block);
    }

    public Vector midPoint()
    {
        return max.clone().add(min).multiply(0.5);
    }

    public Vector getMax()
    {
        return max;
    }

    public Vector getMin()
    {
        return min;
    }

    public void remove(double x1, double y1, double z1, double x2, double y2, double z2)
    {
        getMin().setX(getMin().getX() - x1);
        getMin().setY(getMin().getY() - y1);
        getMin().setZ(getMin().getZ() - z1);

        getMax().setX(getMax().getX() - x2);
        getMax().setY(getMax().getY() - y2);
        getMax().setZ(getMax().getZ() - z2);
    }

    public boolean isInside(Vector position)
    {
        if (position.getX() < getMin().getX() || position.getX() > getMax().getX())
        {
            return false;
        } else if (position.getY() < getMin().getY() || position.getY() > getMax().getY())
        {
            return false;
        } else return !(position.getZ() < getMin().getZ()) && !(position.getZ() > getMax().getZ());
    }

    @Override
    public String toString()
    {
        return "BoundingBox{" +
                "max=" + max +
                ", min=" + min +
                '}';
    }
}
