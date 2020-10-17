package fr.bretzel.bcore.nms.math;

import fr.bretzel.bcore.utils.Reflection;
import fr.bretzel.bcore.utils.reflection.NMSReflection;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.bukkit.Location;
import org.bukkit.World;

import java.lang.reflect.Field;

public class Vec3D
{
    public static final Field VEC_3D_X = Reflection.getField(NMSReflection.CLASS_VEC3D, "x");
    public static final Field VEC_3D_Y = Reflection.getField(NMSReflection.CLASS_VEC3D, "y");
    public static final Field VEC_3D_Z = Reflection.getField(NMSReflection.CLASS_VEC3D, "z");
    private final Object toNMS;
    private double x = 0, y = 0, z = 0;

    public Vec3D(Object o)
    {
        this.toNMS = o;
        x = (double) Reflection.get(VEC_3D_X, o);
        y = (double) Reflection.get(VEC_3D_Y, o);
        z = (double) Reflection.get(VEC_3D_Z, o);
    }

    public Vec3D(double x, double y, double z)
    {
        setX(x);
        setY(y);
        setZ(z);

        toNMS = Reflection.newInstance(NMSReflection.CONSTRUCTOR_VEC3D, getX(), getY(), getZ());
    }

    public Location toLocation(World world)
    {
        return new Location(world, getX(), getY(), getZ());
    }

    public double getX()
    {
        return x;
    }

    public void setX(double x)
    {
        this.x = x;
    }

    public double getY()
    {
        return y;
    }

    public void setY(double y)
    {
        this.y = y;
    }

    public double getZ()
    {
        return z;
    }

    public void setZ(double z)
    {
        this.z = z;
    }

    public Object toNMS()
    {
        return toNMS;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;

        if (!(o instanceof Vec3D)) return false;

        Vec3D vec3D = (Vec3D) o;

        return new EqualsBuilder()
                .append(getX(), vec3D.getX())
                .append(getY(), vec3D.getY())
                .append(getZ(), vec3D.getZ())
                .isEquals();
    }

    @Override
    public int hashCode()
    {
        return new HashCodeBuilder(17, 37)
                .append(getX())
                .append(getY())
                .append(getZ())
                .toHashCode();
    }

    @Override
    public String toString()
    {
        return "Vec3D{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", toNMS=" + toNMS +
                '}';
    }
}
