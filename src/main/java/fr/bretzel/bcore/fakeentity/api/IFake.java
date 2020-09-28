package fr.bretzel.bcore.fakeentity.api;

import fr.bretzel.bcore.nms.entity.MCEntity;
import org.bukkit.entity.Player;

import java.util.List;

public interface IFake
{
    /**
     * @param players can be view the entity
     */
    void sendSpawnTo(Player... players);

    /**
     * @param playerList can be view the entity
     */
    void sendSpawnTo(List<Player> playerList);

    /**
     * @param range of view
     */
    void sendSpawnTo(double range);

    /**
     * @param players can be destroy the entity
     */
    void sendDestroyTo(Player... players);

    /**
     * @param playerList can be destroy the entity
     */
    void sendDestroyTo(List<Player> playerList);

    /**
     * @param range of delete
     */
    void sendDestroyTo(double range);

    void sendSpawnPacket(Player player);

    void sendDestroyPacket(Player player);

    MCEntity getEntity();
}
