package fr.bretzel.bcore.listener;

import fr.bretzel.bcore.BCore;
import fr.bretzel.bcore.listener.player.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class BCoreListener
{
    //Instance of BukkitPlugin Manager
    private static PluginManager MANAGER;

    private static PlayerListener PLAYER_LISTENER;

    public BCoreListener()
    {
        MANAGER = Bukkit.getPluginManager();
    }

    /**
     * Register all Listener
     */
    public static void registerAllListener()
    {
        //Player Listener
        BCore.INSTANCE.logInfo("Enabling: PlayerListener.class");
        MANAGER.registerEvents(PLAYER_LISTENER = new PlayerListener(), BCore.INSTANCE);
    }

    /**
     * Unregister all Listener
     */
    public static void unRegisterAllListener()
    {
        //Player Listener
        PLAYER_LISTENER.unregisterEvents();
    }
}
