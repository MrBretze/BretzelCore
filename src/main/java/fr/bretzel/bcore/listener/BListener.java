package fr.bretzel.bcore.listener;

import org.bukkit.event.Listener;

public abstract class BListener implements Listener
{
    public abstract BListener registerEvents();

    public abstract BListener unregisterEvents();
}
