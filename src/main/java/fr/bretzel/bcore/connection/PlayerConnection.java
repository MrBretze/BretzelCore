package fr.bretzel.bcore.connection;

import fr.bretzel.bcore.connection.channel.ChannelInterceptor;
import fr.bretzel.bcore.connection.packet.Packet;
import fr.bretzel.bcore.player.BPlayer;
import fr.bretzel.bcore.utils.Reflection;
import fr.bretzel.bcore.utils.reflection.EntityReflection;
import fr.bretzel.bcore.utils.reflection.NMSReflection;
import io.netty.channel.Channel;

public class PlayerConnection
{
    private final Object player_connection;

    private final BPlayer bPlayer;
    private final Object network_manager;
    private Channel channel;
    private ChannelInterceptor channelInterceptor;

    public PlayerConnection(BPlayer bPlayer)
    {
        this.bPlayer = bPlayer;
        this.player_connection = Reflection.get(EntityReflection.Player.FIELD_PLAYER_CONNECTION, bPlayer.getNMSPlayer());
        this.network_manager = Reflection.get(EntityReflection.Player.FIELD_NETWORK_MANAGER, player_connection);
    }

    public void sendPacket(Packet packet)
    {
        Reflection.invoke(EntityReflection.Player.METHOD_SEND_PACKET, getNMSInstance(), NMSReflection.CLASS_PACKET.cast(packet.toNMSPacket()));
    }

    public BPlayer getBPlayer()
    {
        return bPlayer;
    }

    public Channel getChannel()
    {
        if (channel == null || !channel.isOpen() || !channel.isActive())
        {
            this.channel = (Channel) Reflection.get(NMSReflection.FIELD_NETWORK_MANAGER_CHANNEL, network_manager);

            if (channel != null && channelInterceptor == null)
            {
                this.channelInterceptor = new ChannelInterceptor(getBPlayer());

                if (this.channel.pipeline().get("packet_handler") != null)
                    this.channel.pipeline().addBefore("packet_handler", "BCORE", getChannelInterceptor());
                else System.out.println("Cannot get 'packet_handler' on the channel !");
            }
        }

        return channel;
    }

    public Object getNMSInstance()
    {
        return player_connection;
    }

    public Object getNetworkManager()
    {
        return network_manager;
    }

    public ChannelInterceptor getChannelInterceptor()
    {
        if (this.channelInterceptor == null)
            getChannel();
        return channelInterceptor;
    }

    @Override
    public String toString()
    {
        return "PlayerConnection{" +
                "player_connection=" + player_connection +
                ", bPlayer=" + bPlayer +
                ", channelInterceptor=" + channelInterceptor +
                '}';
    }
}
