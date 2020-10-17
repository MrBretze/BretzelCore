package fr.bretzel.bcore.utils.maths;

import org.bukkit.util.Vector;

public class VectorUtils
{
    public static Vector rotateVectorCC(Vector vec, Vector axis, double theta)
    {
        double x, y, z;
        double u, v, w;

        x = vec.getX();
        y = vec.getY();
        z = vec.getZ();

        u = axis.getX();
        v = axis.getY();
        w = axis.getZ();

        double v1 = u * x + v * y + w * z;

        double xPrime = u * v1 * (1d - Math.cos(theta))
                + x * Math.cos(theta)
                + (-w * y + v * z) * Math.sin(theta);
        double yPrime = v * v1 * (1d - Math.cos(theta))
                + y * Math.cos(theta)
                + (w * x - u * z) * Math.sin(theta);
        double zPrime = w * v1 * (1d - Math.cos(theta))
                + z * Math.cos(theta)
                + (-v * x + u * y) * Math.sin(theta);

        return new Vector(xPrime, yPrime, zPrime);
    }

    public static boolean intersectRayWithSquare(Vector R1, Vector R2, Vector S1, Vector S2, Vector S3)
    {
        // 1.
        Vector dS21 = S2.subtract(S1);
        Vector dS31 = S3.subtract(S1);
        Vector n = dS21.crossProduct(dS31);

        // 2.
        Vector dR = R1.subtract(R2);

        double ndotdR = n.dot(dR);

        if (Math.abs(ndotdR) < 0.000001D)
        { // Choose your tolerance
            return false;
        }

        double t = -n.dot(R1.subtract(S1)) / ndotdR;
        Vector M = R1.add(dR.multiply(t));

        // 3.
        Vector dMS1 = M.subtract(S1);
        double u = dMS1.dot(dS21);
        double v = dMS1.dot(dS31);

        // 4.
        return (u >= 0.0 && u <= dS21.dot(dS21)
                && v >= 0.0 && v <= dS31.dot(dS31));
    }
}
