package fr.bretzel.bcore.bplugin;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public abstract class BPlugin extends JavaPlugin
{
    private final Logger logger;

    public BPlugin()
    {
        logger = Bukkit.getLogger();
    }

    public void logInfo(String msg)
    {
        getLogger().log(Level.INFO, "[" + getName() + "] " + msg);
    }

    public void logWarning(String msg)
    {
        getLogger().log(Level.WARNING, "[" + getName() + "] " + msg);
    }

    @Override
    public Logger getLogger()
    {
        return logger;
    }
}
