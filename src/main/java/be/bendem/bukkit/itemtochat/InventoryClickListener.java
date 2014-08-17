package be.bendem.bukkit.itemtochat;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * @author bendem
 */
public class InventoryClickListener implements Listener {

    private ItemToChat plugin;

    InventoryClickListener(ItemToChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(e.getClick() != ClickType.MIDDLE || e.getCurrentItem().getType() == Material.AIR) {
            return;
        }
        // TODO Permissions
        plugin.sendItem((Player) e.getWhoClicked(), e.getCurrentItem(), "[item]");
    }

}
