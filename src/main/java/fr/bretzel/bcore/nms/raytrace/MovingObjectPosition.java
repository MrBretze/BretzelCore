package fr.bretzel.bcore.nms.raytrace;

import fr.bretzel.bcore.nms.math.Vec3D;
import fr.bretzel.bcore.utils.Reflection;

import java.lang.reflect.Method;

public class MovingObjectPosition
{
    private static final Class<?> MOVING_OBJECT_POSITION_CLASS = Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "MovingObjectPosition");
    private static final Method GET_VEC3D = Reflection.getMethod(MOVING_OBJECT_POSITION_CLASS, "getPos");
    private final Object nms_object;
    private final Vec3D vec_3d;

    public MovingObjectPosition(Object ob)
    {
        this.nms_object = ob;
        this.vec_3d = new Vec3D(Reflection.invoke(GET_VEC3D, ob));
    }

    public Vec3D getVec3D()
    {
        return vec_3d;
    }

    public Object toNMS()
    {
        return nms_object;
    }
}
