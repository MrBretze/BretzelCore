package fr.bretzel.bcore.nms;

import fr.bretzel.bcore.raytracing.boundingbox.box.BoundingBox;
import fr.bretzel.bcore.utils.Reflection;
import org.bukkit.util.Vector;

import java.lang.reflect.Method;

public class VoxelShape
{
    private static final Class<?> VOXEL_SHAPE_CLASS = Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "VoxelShape");
    private static final Class<?> VOXEL_SHAPES_CLASS = Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "VoxelShapes");
    private static final Method CREATE = Reflection.getMethod(VOXEL_SHAPES_CLASS, "create", Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE, Double.TYPE);
    private static final Method GET_BOUNDING_BLOCK = Reflection.getMethod(VOXEL_SHAPE_CLASS, "getBoundingBox");
    private final Object nms_object;
    private final Object nms_bounding_box;
    private BoundingBox boundingBox;

    public VoxelShape(double minx, double miny, double minz, double maxx, double maxy, double maxz)
    {
        Vector min = new Vector(minx, miny, minz);
        Vector max = new Vector(maxx, maxy, maxz);

        nms_object = Reflection.invoke(CREATE, null, min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());
        nms_bounding_box = Reflection.invoke(GET_BOUNDING_BLOCK, nms_object);
        this.boundingBox = new BoundingBox(min, max);
    }

    public VoxelShape(BoundingBox boundingBox)
    {
        Vector min = boundingBox.getMin();
        Vector max = boundingBox.getMax();

        nms_object = Reflection.invoke(CREATE, null, min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());
        nms_bounding_box = Reflection.invoke(GET_BOUNDING_BLOCK, nms_object);
        this.boundingBox = boundingBox;
    }

    public VoxelShape(Object o)
    {
        this.nms_object = o;
        this.nms_bounding_box = Reflection.invoke(GET_BOUNDING_BLOCK, nms_object);
    }

    public BoundingBox getBoundingBox()
    {
        return boundingBox;
    }

    public Object getBoundingNmsBox()
    {
        return nms_bounding_box;
    }

    public Object toNMS()
    {
        return VOXEL_SHAPE_CLASS.cast(nms_object);
    }
}
