package fr.bretzel.bcore.connection.packet;

public abstract class Packet
{
    private Object nmsPacket;

    public Packet()
    {
    }

    protected void setNMSPacket(Object nmsPacket)
    {
        this.nmsPacket = nmsPacket;
    }

    public Object toNMSPacket()
    {
        return nmsPacket;
    }

    public abstract Class<?> getNMSClass();
}
