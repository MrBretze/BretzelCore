package fr.bretzel.bcore.nms.raytrace;


import fr.bretzel.bcore.utils.Reflection;

public enum FluidCollisionOption
{
    //Ignore All Fluid
    NONE(),
    //Only Source Fluid
    SOURCE_ONLY(),
    //All Fluid
    ANY();

    private final Object toNMS;

    FluidCollisionOption()
    {
        Class<Enum> FLUID_COLLISION_OPTION_CLASS = (Class<Enum>) Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "RayTrace$FluidCollisionOption");
        this.toNMS = Enum.valueOf(FLUID_COLLISION_OPTION_CLASS, name());
    }

    public Object toNMS()
    {
        return toNMS;
    }
}
