package fr.bretzel.bcore;

import fr.bretzel.bcore.bplugin.BPlugin;
import fr.bretzel.bcore.listener.BCoreListener;
import org.bukkit.Bukkit;

public final class BCore extends BPlugin
{
    private static String version;

    public static String getVersion()
    {
        return version;
    }

    public static BCore INSTANCE;

    public BCore()
    {
        String[] versionArray = Bukkit.getServer().getClass().getName().replace('.', ',').split(",");
        if (versionArray.length >= 4)
        {
            version = versionArray[3];
        } else
        {
            version = "";
        }

        INSTANCE = this;
    }

    @Override
    public void onEnable()
    {
        logInfo("Registering all Listener");
        BCoreListener.registerAllListener();
    }

    @Override
    public void onDisable()
    {
    }
}
