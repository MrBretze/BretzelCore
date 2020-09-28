package fr.bretzel.bcore.nms.packet.out;

import fr.bretzel.bcore.nms.entity.MCEntity;
import fr.bretzel.bcore.nms.packet.Packet;
import fr.bretzel.bcore.utils.Reflection;

import java.lang.reflect.Constructor;

public class PacketPlayOutEntityTeleport extends Packet
{

    private MCEntity mcEntity;

    public PacketPlayOutEntityTeleport(MCEntity mcEntity)
    {
        this.mcEntity = mcEntity;

        Class<?> packetPlayOutEntityTeleport = Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "PacketPlayOutEntityTeleport");
        Constructor<?> constructor = Reflection.getConstructor(packetPlayOutEntityTeleport, Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "Entity"));
        setNMSPacket(Reflection.newInstance(constructor, mcEntity.toNMSEntity()));
    }


    public MCEntity getMcEntity()
    {
        return mcEntity;
    }
}
