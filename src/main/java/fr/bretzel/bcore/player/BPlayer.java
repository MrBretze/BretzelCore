package fr.bretzel.bcore.player;

import fr.bretzel.bcore.connection.PlayerConnection;
import fr.bretzel.bcore.utils.Reflection;
import fr.bretzel.bcore.utils.reflection.CraftBukkitReflection;
import fr.bretzel.bcore.utils.reflection.EntityReflection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

public class BPlayer
{
    //////////////////////////STATIC VARIABLE\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\
    private static final HashMap<UUID, BPlayer> playerCachedMap = new HashMap<>();
    private final UUID uniqueId;
    private final PlayerConnection player_connection;
    private final Object nms_player;
    private Player player;
    private Location location;

    protected BPlayer(Player player)
    {
        this.uniqueId = player.getUniqueId();
        this.player = player;
        this.nms_player = Reflection.invoke(CraftBukkitReflection.METHOD_PLAYER_GET_HANDLE, CraftBukkitReflection.CLASS_PLAYER.cast(getPlayer()));

        this.player_connection = new PlayerConnection(this);
    }

    protected BPlayer(UUID player)
    {
        this.uniqueId = player;
        this.nms_player = Reflection.invoke(CraftBukkitReflection.METHOD_PLAYER_GET_HANDLE, CraftBukkitReflection.CLASS_PLAYER.cast(getPlayer()));

        this.player_connection = new PlayerConnection(this);
    }
    //////////////////////////END STATIC VARIABLE\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    public static BPlayer getBPlayer(Player player)
    {
        if (playerCachedMap.containsKey(player.getUniqueId()))
            return playerCachedMap.get(player.getUniqueId());

        register(player);

        return playerCachedMap.get(player.getUniqueId());
    }

    public static BPlayer getBPlayer(UUID uuid)
    {
        if (playerCachedMap.containsKey(uuid))
            return playerCachedMap.get(uuid);

        register(uuid);
        return playerCachedMap.get(uuid);
    }

    public static void register(UUID player)
    {
        playerCachedMap.put(player, new BPlayer(player));
    }

    public static void register(Player player)
    {
        if (playerCachedMap.containsKey(player.getUniqueId()))
            playerCachedMap.get(player.getUniqueId());

        playerCachedMap.put(player.getUniqueId(), new BPlayer(player));
    }

    public static void unRegister(Player player)
    {
        unRegister(player.getUniqueId());
    }

    public static void unRegister(UUID player)
    {
        if (playerCachedMap.containsKey(player))
        {
            playerCachedMap.get(player).unLoad();
            playerCachedMap.remove(player);
        }
    }

    public static void clearCash()
    {
        for (BPlayer bPlayer : playerCachedMap.values())
            bPlayer.unLoad();

        playerCachedMap.clear();
    }

    public Player getPlayer()
    {
        if (player == null || !player.isOnline())
            player = Bukkit.getPlayer(uniqueId);

        return player;
    }

    public Object getNMSPlayer()
    {
        return nms_player;
    }

    public PlayerConnection getPlayerConnection()
    {
        return player_connection;
    }

    public int getPing()
    {
        Object o = Reflection.get(EntityReflection.Player.FIELD_PING, getNMSPlayer());
        if (o instanceof Integer) return (int) o;
        return -1;
    }

    /**
     * I don't use the Bukkit location but a detect manually with the packet
     *
     * @return The Location of the player
     */
    public Location getLocation()
    {
        return location;
    }

    /**
     * Set the location of the player
     *
     * @param location the new location
     */
    public void setLocation(Location location)
    {
        this.location = location;
    }

    protected void unLoad()
    {
        if (getPlayerConnection().getChannel().pipeline().get(getPlayerConnection().getChannelInterceptor().getClass()) != null)
            getPlayerConnection().getChannel().pipeline().remove(getPlayerConnection().getChannelInterceptor());
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (!(o instanceof BPlayer)) return false;

        BPlayer bPlayer = (BPlayer) o;

        return Objects.equals(uniqueId, bPlayer.uniqueId);
    }

    @Override
    public int hashCode()
    {
        return uniqueId != null ? uniqueId.hashCode() : 0;
    }

    @Override
    public String toString()
    {
        return "BPlayer{" +
                "uniqueId=" + uniqueId +
                ", player=" + player +
                ", location=" + location +
                '}';
    }
}
