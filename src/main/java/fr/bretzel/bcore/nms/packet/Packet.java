package fr.bretzel.bcore.nms.packet;

public abstract class Packet
{
    private Object nmsPacket;

    protected void setNMSPacket(Object nmsPacket)
    {
        this.nmsPacket = nmsPacket;
    }

    public Object toNMSPacket()
    {
        return nmsPacket;
    }
}
