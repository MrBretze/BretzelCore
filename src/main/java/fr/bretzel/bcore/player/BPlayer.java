package fr.bretzel.bcore.player;

import fr.bretzel.bcore.bplugin.BPlugin;
import fr.bretzel.bcore.nms.packet.Packet;
import fr.bretzel.bcore.player.persistence.PersistenceData;
import fr.bretzel.bcore.utils.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
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

    protected BPlayer(UUID player)
    {
        this.uniqueId = player;
    }
    //////////////////////////END STATIC VARIABLE\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

    /////////////////////////Method, Field, and Class init\\\\\\\\\\\\\\\\\\\\\\\\\\\
    private static final Class<?> packetClass = Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "Packet");
    private static final Class<?> craftplayerClass = Reflection.getClass(Reflection.ClassType.CRAFT_BUKKIT_ENTITY, "CraftPlayer");
    private static final Class<?> entity_player = Reflection.getClass(Reflection.ClassType.MINECRAFT_SERVER, "EntityPlayer");

    private static final Method getHandle = Reflection.getMethod(craftplayerClass, "getHandle");

    private static final Field player_connection = Reflection.getField(entity_player, "playerConnection");

    private static final Method send_packet = Reflection.getMethod(player_connection.getType(), "sendPacket", packetClass);
    ////////////////////End of Method, Field, and Class init\\\\\\\\\\\\\\\\\\\\\\\\\

    private final UUID uniqueId;

    private final HashMap<BPlugin, PersistenceData> persistenceDataMap = new HashMap<>();

    public Player getPlayer()
    {
        return Bukkit.getPlayer(uniqueId);
    }

    public void addPersistenceData(BPlugin bPlugin, PersistenceData data)
    {

    }

    public void sendPacket(Packet packet)
    {
        Object player = Reflection.invoke(getHandle, craftplayerClass.cast(getPlayer()));
        Object playerConnection = Reflection.get(player_connection, player);
        Reflection.invoke(send_packet, playerConnection, packetClass.cast(packet.toNMSPacket()));
    }
}
