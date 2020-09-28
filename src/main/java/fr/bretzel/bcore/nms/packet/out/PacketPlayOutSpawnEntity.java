package fr.bretzel.bcore.nms.packet.out;

import fr.bretzel.bcore.nms.entity.MCEntity;
import fr.bretzel.bcore.nms.packet.Packet;
import fr.bretzel.bcore.utils.reflection.Reflection;

import java.lang.reflect.Constructor;

public class PacketPlayOutSpawnEntity extends Packet
{

    private final MCEntity mcEntity;

    public PacketPlayOutSpawnEntity(MCEntity mcEntity)
    {
        this.mcEntity = mcEntity;
        Class<?> packetPlayOutSpawnEntity = Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "PacketPlayOutSpawnEntity");
        Constructor<?> constructor = Reflection.getConstructor(packetPlayOutSpawnEntity, Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "Entity"));
        setNMSPacket(Reflection.newInstance(constructor, mcEntity.toNMSEntity()));
    }

    public MCEntity getMcEntity()
    {
        return mcEntity;
    }
}
