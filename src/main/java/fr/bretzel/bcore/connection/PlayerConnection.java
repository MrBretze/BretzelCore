package fr.bretzel.bcore.connection;

import fr.bretzel.bcore.nms.packet.Packet;
import fr.bretzel.bcore.player.BPlayer;
import fr.bretzel.bcore.utils.reflection.NMSReflection;
import fr.bretzel.bcore.utils.reflection.Reflection;
import io.netty.channel.Channel;

public class PlayerConnection
{
    private final Object player_connection;

    private final BPlayer bPlayer;
    private Channel channel;

    public PlayerConnection(BPlayer bPlayer)
    {
        this.bPlayer = bPlayer;
        this.player_connection = Reflection.get(NMSReflection.FIELD_PLAYER_CONNECTION, bPlayer.getNMSPlayer());

    }

    public void sendPacket(Packet packet)
    {
        Reflection.invoke(NMSReflection.METHOS_SEND_PACKET, getNMSInstance(), NMSReflection.CLASS_PACKET.cast(packet.toNMSPacket()));
    }

    public BPlayer getBPlayer()
    {
        return bPlayer;
    }

    public Channel getChannel()
    {
        return channel;
    }

    public Object getNMSInstance()
    {
        return player_connection;
    }
}
