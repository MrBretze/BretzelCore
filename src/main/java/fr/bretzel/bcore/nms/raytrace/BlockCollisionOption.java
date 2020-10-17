package fr.bretzel.bcore.nms.raytrace;


import fr.bretzel.bcore.utils.Reflection;

public enum BlockCollisionOption
{
    COLLIDER(),
    OUTLINE(),
    VISUAL();

    private final Object toNMS;

    BlockCollisionOption()
    {
        Class<Enum> BLOCK_COLLISION_OPTION_CLASS = (Class<Enum>) Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "RayTrace$BlockCollisionOption");
        this.toNMS = Enum.valueOf(BLOCK_COLLISION_OPTION_CLASS, name());
    }

    public Object toNMS()
    {
        return toNMS;
    }
}
