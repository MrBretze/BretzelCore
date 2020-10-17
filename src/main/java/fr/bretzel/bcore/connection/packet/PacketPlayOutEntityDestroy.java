package fr.bretzel.bcore.connection.packet;

import fr.bretzel.bcore.nms.entity.MCEntity;
import fr.bretzel.bcore.utils.Reflection;

import java.lang.reflect.Constructor;

public class PacketPlayOutEntityDestroy extends Packet
{
    private final MCEntity[] mcEntity;
    private final int[] ids;

    public PacketPlayOutEntityDestroy(MCEntity... mcEntities)
    {
        this.mcEntity = mcEntities;
        ids = new int[mcEntities.length];

        for (int i = 0; i < mcEntities.length; i++)
            ids[i] = mcEntities[i].getId();

        Constructor<?> constructor = Reflection.getConstructor(getNMSClass(), getIds().getClass());
        setNMSPacket(Reflection.newInstance(constructor, getIds()));
    }

    public int[] getIds()
    {
        return ids;
    }

    public MCEntity[] getMcEntity()
    {
        return mcEntity;
    }

    @Override
    public Class<?> getNMSClass()
    {
        return Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "PacketPlayOutSpawnEntity");
    }
}
