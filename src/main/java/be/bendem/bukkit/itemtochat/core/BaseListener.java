package be.bendem.bukkit.itemtochat.core;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author bendem
 */
public class BaseListener implements Listener {

    public BaseListener(JavaPlugin plugin) {
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

}
