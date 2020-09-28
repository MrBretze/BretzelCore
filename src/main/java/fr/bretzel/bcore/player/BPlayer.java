package fr.bretzel.bcore.player;

import fr.bretzel.bcore.connection.PlayerConnection;
import fr.bretzel.bcore.utils.reflection.CraftBukkitReflection;
import fr.bretzel.bcore.utils.reflection.Reflection;
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

    public static BPlayer getBPlayer(Player player)
    {
        return getBPlayer(player.getUniqueId());
    }

    public static BPlayer getBPlayer(UUID uuid)
    {
        if (playerCachedMap.containsKey(uuid))
            return playerCachedMap.get(uuid);

        BPlayer bPlayer;
        playerCachedMap.put(uuid, bPlayer = new BPlayer(uuid));
        return bPlayer;
    }
    //////////////////////////END STATIC VARIABLE\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    private final UUID uniqueId;
    private final PlayerConnection player_connection;
    private Player player;
    private final Object nms_player;

    private Location location;

    protected BPlayer(Player player)
    {
        this.uniqueId = player.getUniqueId();
        this.player = player;

        this.player_connection = new PlayerConnection(this);
        nms_player = Reflection.invoke(CraftBukkitReflection.METHOD_PLAYER_GET_HANDLE, CraftBukkitReflection.CLASS_PLAYER.cast(getPlayer()));
    }

    protected BPlayer(UUID player)
    {
        this.uniqueId = player;
        this.player_connection = new PlayerConnection(this);

        nms_player = Reflection.invoke(CraftBukkitReflection.METHOD_PLAYER_GET_HANDLE, CraftBukkitReflection.CLASS_PLAYER.cast(getPlayer()));
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
}
