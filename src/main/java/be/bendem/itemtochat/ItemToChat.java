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
        // TODO Make the plugin creative friendly (at the moment, middle click is not detected on creative)
        // TODO Add a way to send item trough chat using a placeholder
        // Like "Wanna see my [item]" where item is replaced by item in hand
        getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
    }

}
