package fr.bretzel.bcore.connection.channel;

import fr.bretzel.bcore.connection.PacketInterceptor;
import fr.bretzel.bcore.connection.packet.PacketData;
import fr.bretzel.bcore.player.BPlayer;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;

import java.util.ArrayList;
import java.util.List;

public class ChannelInterceptor extends ChannelDuplexHandler
{
    private final List<PacketInterceptor> packetInterceptors = new ArrayList<>();
    private final BPlayer bPlayer;

    public ChannelInterceptor(BPlayer bPlayer)
    {
        this.bPlayer = bPlayer;
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception
    {
        PacketInterceptor last_packet = null;
        try
        {
            if (packetInterceptors.size() > 0)
                for (PacketInterceptor packet_interceptor : packetInterceptors)
                {
                    last_packet = packet_interceptor;
                    packet_interceptor.onServerSendPacket(new PacketData(msg));
                }
        } catch (Exception e)
        {
            if (last_packet != null)
            {
                System.out.println("Error to execute packet interceptor: " + last_packet.getClass().getSimpleName());
                for (StackTraceElement element : e.getStackTrace())
                    System.out.println(element.toString());
            }
        }

        super.write(ctx, msg, promise);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception
    {
        PacketInterceptor last_packet = null;
        try
        {
            if (packetInterceptors.size() > 0)
                for (PacketInterceptor packet_interceptor : packetInterceptors)
                {
                    last_packet = packet_interceptor;
                    packet_interceptor.onClientSendPacket(new PacketData(msg));
                }
        } catch (Exception e)
        {
            if (last_packet != null)
            {
                System.out.println("Error to execute packet interceptor: " + last_packet.getClass().getSimpleName());
                for (StackTraceElement element : e.getStackTrace())
                    System.out.println(element.toString());
            }
        }

        super.channelRead(ctx, msg);
    }

    public BPlayer getBPlayer()
    {
        return bPlayer;
    }

    public void addPacketInterceptor(PacketInterceptor interceptor)
    {
        interceptor.setBPlayer(getBPlayer());
        packetInterceptors.add(interceptor);
    }

    @Override
    public String toString()
    {
        return "ChannelInterceptor{" +
                "packetInterceptors=" + packetInterceptors +
                ", bPlayer=" + bPlayer +
                '}';
    }
}
