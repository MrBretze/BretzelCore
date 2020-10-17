package fr.bretzel.bcore.listener.player;

import fr.bretzel.bcore.BCore;
import fr.bretzel.bcore.listener.BListener;
import fr.bretzel.bcore.player.BPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerListener extends BListener
{

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        BPlayer.register(event.getPlayer());
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event)
    {
        BPlayer.unRegister(event.getPlayer());
    }

    @Override
    public BListener registerEvents()
    {
        getPluginManager().registerEvents(this, getPlugin());
        return this;
    }

    @Override
    public BListener unregisterEvents()
    {
        PlayerQuitEvent.getHandlerList().unregister(this);
        PlayerJoinEvent.getHandlerList().unregister(this);
        return this;
    }

    @Override
    public JavaPlugin getPlugin()
    {
        return BCore.INSTANCE;
    }
}
