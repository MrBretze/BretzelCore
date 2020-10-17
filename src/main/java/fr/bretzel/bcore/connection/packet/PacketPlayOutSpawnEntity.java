package fr.bretzel.bcore.connection.packet;

import fr.bretzel.bcore.nms.entity.MCEntity;
import fr.bretzel.bcore.utils.Reflection;

import java.lang.reflect.Constructor;

public class PacketPlayOutSpawnEntity extends Packet
{

    private final MCEntity mcEntity;

    public PacketPlayOutSpawnEntity(MCEntity mcEntity)
    {
        this.mcEntity = mcEntity;
        Constructor<?> constructor = Reflection.getConstructor(getNMSClass(), Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "Entity"));
        setNMSPacket(Reflection.newInstance(constructor, mcEntity.toNMSEntity()));
    }

    public MCEntity getMcEntity()
    {
        return mcEntity;
    }

    @Override
    public Class<?> getNMSClass()
    {
        return Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "PacketPlayOutSpawnEntity");
    }
}
