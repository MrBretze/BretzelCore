package fr.bretzel.bcore.nms.raytrace;

import fr.bretzel.bcore.utils.Reflection;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.BlockFace;

import java.lang.reflect.Method;

public class MovingObjectPositionBlock extends MovingObjectPosition
{
    private static final Class<?> MOVING_OBJECT_POSITION_BLOCK_CLASS = Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "MovingObjectPositionBlock");
    private static final Class<?> CRAFT_BLOCK_CLASS = Reflection.getClass(Reflection.ClassType.CRAFT_BUKKIT, "block.CraftBlock");
    private static final Class<?> ENUM_DIRECTION_CLASS = Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "EnumDirection");
    private static final Class<?> BASE_BLOCK_POSITION_CLASS = Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "BaseBlockPosition");
    private static final Method GET_BLOCK_POSITION = Reflection.getMethod(MOVING_OBJECT_POSITION_BLOCK_CLASS, "getBlockPosition");
    private static final Method GET_DIRECTION = Reflection.getMethod(MOVING_OBJECT_POSITION_BLOCK_CLASS, "getDirection");
    private static final Method NOTCH_TO_BLOCK_FACE = Reflection.getMethod(CRAFT_BLOCK_CLASS, "notchToBlockFace", ENUM_DIRECTION_CLASS);
    private static final Method GET_X = Reflection.getMethod(BASE_BLOCK_POSITION_CLASS, "getX");
    private static final Method GET_Y = Reflection.getMethod(BASE_BLOCK_POSITION_CLASS, "getY");
    private static final Method GET_Z = Reflection.getMethod(BASE_BLOCK_POSITION_CLASS, "getZ");
    private final Object nms_object;
    private final BlockFace block_face;

    public MovingObjectPositionBlock(Object ob)
    {
        super(ob);
        this.nms_object = ob;
        block_face = (BlockFace) Reflection.invoke(NOTCH_TO_BLOCK_FACE, null, Reflection.invoke(GET_DIRECTION, ob));
    }

    @Override
    public Object toNMS()
    {
        return nms_object;
    }

    public BlockFace getBlockFace()
    {
        return block_face;
    }

    public Location getBlockLocation(World world)
    {
        Object o = BASE_BLOCK_POSITION_CLASS.cast(Reflection.invoke(GET_BLOCK_POSITION, toNMS()));
        int x = (int) Reflection.invoke(GET_X, o);
        int y = (int) Reflection.invoke(GET_Y, o);
        int z = (int) Reflection.invoke(GET_Z, o);
        return new Location(world, x, y, z);
    }
}
