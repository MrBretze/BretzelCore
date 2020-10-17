package fr.bretzel.bcore.connection;

import fr.bretzel.bcore.connection.packet.PacketData;
import fr.bretzel.bcore.player.BPlayer;
import org.bukkit.entity.Player;

public abstract class PacketInterceptor
{
    private BPlayer bPlayer;

    public abstract void onClientSendPacket(PacketData packetData);

    public abstract void onServerSendPacket(PacketData packetData);

    public BPlayer getBPlayer()
    {
        return bPlayer;
    }

    public void setBPlayer(BPlayer bPlayer)
    {
        this.bPlayer = bPlayer;
    }

    public Player getPlayer()
    {
        return getBPlayer().getPlayer();
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "{" +
                "bPlayer=" + bPlayer +
                '}';
    }
}
