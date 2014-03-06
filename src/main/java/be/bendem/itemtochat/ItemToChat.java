package be.bendem.itemtochat;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * ItemToChat for Bukkit
 *
 * @author bendem
 */
public class ItemToChat extends JavaPlugin {

    public Logger logger;

    @Override
    public void onEnable() {
        logger = getLogger();
        saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
    }

}
