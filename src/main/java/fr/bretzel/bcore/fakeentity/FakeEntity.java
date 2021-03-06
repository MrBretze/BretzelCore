package fr.bretzel.bcore.fakeentity;

import fr.bretzel.bcore.connection.packet.PacketPlayOutEntityDestroy;
import fr.bretzel.bcore.connection.packet.PacketPlayOutEntityTeleport;
import fr.bretzel.bcore.connection.packet.PacketPlayOutSpawnEntity;
import fr.bretzel.bcore.fakeentity.api.IFake;
import fr.bretzel.bcore.player.BPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public abstract class FakeEntity implements IFake
{
    private static final CopyOnWriteArrayList<FakeEntity> allFakeEntity = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<Player> playerHasEntity = new CopyOnWriteArrayList<>();
    private Location location;

    public FakeEntity(Location location)
    {
        this.location = location;
        allFakeEntity.add(this);
    }

    public static CopyOnWriteArrayList<FakeEntity> getAllFakeEntity()
    {
        return allFakeEntity;
    }

    @Override
    public void sendSpawnTo(Player... players)
    {
        sendSpawnTo(Arrays.asList(players));
    }

    @Override
    public void sendSpawnTo(List<Player> playerList)
    {
        playerList.forEach(this::sendSpawnPacket);
    }

    @Override
    public void sendSpawnTo(double range)
    {
        sendSpawnTo(Objects.requireNonNull(getLocation().getWorld()).getPlayers().stream().filter(player -> player.getLocation().distance(getLocation()) <= range).collect(Collectors.toList()));
    }

    @Override
    public void sendDestroyTo(double range)
    {
        sendDestroyTo(Objects.requireNonNull(getLocation().getWorld()).getPlayers().stream().filter(player -> player.getLocation().distance(getLocation()) <= range).collect(Collectors.toList()));
    }

    @Override
    public void sendDestroyTo(List<Player> playerList)
    {
        playerList.forEach(this::sendDestroyPacket);
    }

    @Override
    public void sendDestroyTo(Player... players)
    {
        sendDestroyTo(Arrays.asList(players));
    }

    public Location getLocation()
    {
        return location;
    }

    public void setLocation(Location location)
    {
        this.location = location;
        getEntity().setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
    }

    @Override
    public void sendSpawnPacket(Player player)
    {
        if (getPlayerHasEntity().contains(player))
            return;

        getPlayerHasEntity().add(player);
        BPlayer.getBPlayer(player).getPlayerConnection().sendPacket(new PacketPlayOutSpawnEntity(getEntity()));
    }

    @Override
    public void sendDestroyPacket(Player player)
    {
        if (!getPlayerHasEntity().contains(player) || !player.isOnline())
            return;
        BPlayer.getBPlayer(player).getPlayerConnection().sendPacket(new PacketPlayOutEntityDestroy(getEntity()));
    }

    public void teleport(Location location)
    {
        setLocation(location);
        getPlayerHasEntity().forEach(player -> BPlayer.getBPlayer(player).getPlayerConnection().sendPacket(new PacketPlayOutEntityTeleport(getEntity())));
    }

    public CopyOnWriteArrayList<Player> getPlayerHasEntity()
    {
        return playerHasEntity;
    }
}
