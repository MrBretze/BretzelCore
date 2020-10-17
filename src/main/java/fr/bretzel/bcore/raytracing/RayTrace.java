package fr.bretzel.bcore.raytracing;

import fr.bretzel.bcore.nms.BWorld;
import fr.bretzel.bcore.nms.math.Vec3D;
import fr.bretzel.bcore.nms.raytrace.BlockCollisionOption;
import fr.bretzel.bcore.nms.raytrace.FluidCollisionOption;
import fr.bretzel.bcore.nms.raytrace.MovingObjectPositionBlock;
import fr.bretzel.bcore.raytracing.boundingbox.BoundingBoxManager;
import fr.bretzel.bcore.raytracing.boundingbox.box.BoundingBox;
import fr.bretzel.bcore.utils.BlockTesterList;
import fr.bretzel.bcore.utils.Reflection;
import fr.bretzel.bcore.utils.reflection.EntityReflection;
import fr.bretzel.bcore.utils.reflection.NMSReflection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Bell;
import org.bukkit.block.data.type.Fence;
import org.bukkit.block.data.type.Gate;
import org.bukkit.block.data.type.Wall;
import org.bukkit.util.Vector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.function.Predicate;

public class RayTrace
{
    private static final BlockTesterList custom_bounding_boxs = new BlockTesterList();

    private static final Class<?> RAYTRACE_CLASS = Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "RayTrace");
    private static final Class<?> I_BLOCK_ACCESS_CLASS = Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "IBlockAccess");
    private static final Method RAY_TRACE = Reflection.getMethod(I_BLOCK_ACCESS_CLASS, "rayTrace", RAYTRACE_CLASS);

    static
    {
        custom_bounding_boxs.add(block -> block.getBlockData() instanceof Bell);
        custom_bounding_boxs.add(block -> block.getBlockData() instanceof Fence);
        custom_bounding_boxs.add(block -> block.getType() == Material.END_ROD);
        custom_bounding_boxs.add(block -> block.getBlockData() instanceof Fence && !(block.getBlockData() instanceof Wall));
        custom_bounding_boxs.add(block -> block.getBlockData() instanceof Gate);
        //replaceVanillaShape(Material.LECTERN, new VoxelShape(0, 0, 0, 1, 1, 1));
    }

    public static RayTraceResult rayTraceBlocks(Location start, Vector direction, double distance, FluidCollisionOption fluidCollisionMode, BlockCollisionOption blockCollisionOption, Predicate<Location> predicate)
    {
        start.checkFinite();
        direction.checkFinite();

        //BWorld Instance to get Minecraft Server World or CraftBukkit World or Bukkit World
        BWorld bworld = new BWorld(start.getWorld());

        //Hybride RayTracing/RayCasting
        boolean hybride = false;

        final Location real_start = start.clone();
        final Location real_end = real_start.clone().add(direction.clone().normalize().multiply(distance));

        do
        {
            //If need to use the old RayTracing for custom hit box
            if (!hybride)
            {
                //The Vanilla Pos
                Vec3D start_pos = new Vec3D(start.getX(), start.getY(), start.getZ());
                //The End Pos
                Vec3D end_pos = new Vec3D(real_end.getX(), real_end.getY(), real_end.getZ());

                //Use the Minecraft Server RayTracing for efficient RayTracing
                Object nms_raytrace = Reflection.newInstance(getRayTraceConstructor(fluidCollisionMode, blockCollisionOption),
                        start_pos.toNMS(),
                        end_pos.toNMS(),
                        blockCollisionOption.toNMS(),
                        fluidCollisionMode.toNMS(), null);

                //Result of RayTracing translated by BCore
                MovingObjectPositionBlock movingObjectPosition = new MovingObjectPositionBlock(Reflection.invoke(RAY_TRACE, bworld.getNMSWorld(), nms_raytrace));
                //TheBlockFace
                BlockFace block_face = movingObjectPosition.getBlockFace();
                //The exact hit location
                Vec3D vec_hit_location = movingObjectPosition.getVec3D();
                //Hit location -> Bukkit Location
                Location hit_location = vec_hit_location.toLocation(bworld.getBukkitWorld()).subtract(block_face.getDirection().multiply(0.003F));

                //The Hit Block
                Block hit_block = hit_location.getBlock();

                //if the block has a custom BondingBox with the Model or if the predicate is true
                if (custom_bounding_boxs.contains(hit_block) || !predicate.test(hit_location))
                {
                    //Go to the RayCasting
                    hybride = true;
                    start = hit_location;
                } else
                {
                    Bukkit.broadcastMessage("Return to Mojang RT");
                    //Return the hit
                    return new RayTraceResult(start, hit_location, direction);
                }
            } else
            {
                //Accuracy of old RayTracing
                double accuracy = 0.01D;
                //The vector of progression of two point
                Vector progress = direction.clone().normalize().multiply(new Vector(accuracy, accuracy, accuracy));
                //Current point
                Location point_progress = start.clone().add(progress);
                double max_distance = start.clone().distance(real_end);

                //The BoundingBox
                BoundingBox bounding_box = BoundingBoxManager.get(point_progress.getBlock());
                //The Current Block State
                String block_sate = point_progress.getBlock().getBlockData().getAsString();

                Block point_block = point_progress.getBlock();

                //RayCastingLoop
                while (real_start.distance(point_progress) <= max_distance && custom_bounding_boxs.contains(point_block))
                {
                    //Move the point to the direction
                    point_progress.add(progress);

                    //The block state of current test
                    point_block = point_progress.getBlock();

                    //Get a new bonding box
                    if (!point_block.getBlockData().getAsString().equalsIgnoreCase(block_sate))
                    {
                        bounding_box = BoundingBoxManager.get(point_block);
                        block_sate = point_block.getBlockData().getAsString();
                    }

                    //Test example, if point_progress is a grass_block and the result of test is true, we ignore the block and go to the next
                    //If the test is true and if the point is in bounding box, it's a hit and return to the result
                    if (!predicate.test(point_progress) && bounding_box.translate(point_block).isInside(point_progress.toVector()))
                    {
                        return new RayTraceResult(start, point_progress.clone(), direction);
                    } else if (!custom_bounding_boxs.contains(point_block))
                    {
                        //Return to the mojang RayTracing
                        start = point_progress.clone();
                        //reset the hybride value
                        hybride = false;
                        break;
                    }
                }
            }
        } while (hybride);

        return new RayTraceResult(start, start.clone().add(direction), direction);
    }

    protected static Constructor<?> getRayTraceConstructor(FluidCollisionOption fluidCollisionOption, BlockCollisionOption blockCollisionOption)
    {
        return Reflection.getConstructor(RAYTRACE_CLASS,
                NMSReflection.CLASS_VEC3D,
                NMSReflection.CLASS_VEC3D,
                blockCollisionOption.toNMS().getClass(),
                fluidCollisionOption.toNMS().getClass(),
                EntityReflection.CLASS_ENTITY);
    }
}
