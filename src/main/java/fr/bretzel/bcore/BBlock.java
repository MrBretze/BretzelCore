package fr.bretzel.bcore;

import org.bukkit.block.Block;

public class BBlock
{
    /*
     * START OF STATIC MEMBERS
     */

    //From PaperMC
    /**
     * Returns the specified block coordinates packed into a long value
     * <p>
     * The return value can be computed as follows:
     * <br>
     * {@code long value = ((long)x & 0x7FFFFFF) | (((long)z & 0x7FFFFFF) << 27) | ((long)y << 54);}
     * </p>
     *
     * <p>
     * And may be unpacked as follows:
     * <br>
     * {@code int x = (int) ((packed << 37) >> 37);}
     * <br>
     * {@code int y = (int) (packed >>> 54);}
     * <br>
     * {@code int z = (int) ((packed << 10) >> 37);}
     * </p>
     *
     * @return This block's x, y, and z coordinates packed into a long value
     */
    public static long getBlockKey(int x, int y, int z) {
        return ((long)x & 0x7FFFFFF) | (((long)z & 0x7FFFFFF) << 27) | ((long)y << 54);
    }

    /**
     * Returns the x component from the packed value.
     * @param packed The packed value, as computed by {@link BBlock#getBlockKey(int, int, int)}
     * @see BBlock#getBlockKey(int, int, int)
     * @return The x component from the packed value.
     */
    public static int getBlockKeyX(long packed) {
        return (int) ((packed << 37) >> 37);
    }

    /**
     * Returns the y component from the packed value.
     * @param packed The packed value, as computed by {@link BBlock#getBlockKey(int, int, int)}
     * @see BBlock#getBlockKey(int, int, int)
     * @return The y component from the packed value.
     */
    public static int getBlockKeyY(long packed) {
        return (int) (packed >>> 54);
    }

    /**
     * Returns the z component from the packed value.
     * @param packed The packed value, as computed by {@link BBlock#getBlockKey(int, int, int)}
     * @see BBlock#getBlockKey(int, int, int)
     * @return The z component from the packed value.
     */
    public static int getBlockKeyZ(long packed) {
        return (int) ((packed << 10) >> 37);
    }

    /*
     * END OF STATIC MEMBERS
     */
}
