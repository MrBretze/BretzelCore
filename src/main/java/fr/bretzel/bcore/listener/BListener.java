package fr.bretzel.bcore.listener;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class BListener implements Listener
{
    public abstract BListener registerEvents();

    public abstract BListener unregisterEvents();

    public abstract JavaPlugin getPlugin();

    public PluginManager getPluginManager()
    {
        return Bukkit.getPluginManager();
    }
}
