package be.bendem.bukkit.itemtochat.events;

import be.bendem.bukkit.itemtochat.ItemToChat;
import be.bendem.bukkit.itemtochat.core.BaseListener;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * @author bendem
 */
public class InventoryClickListener extends BaseListener {

    private ItemToChat plugin;

    public InventoryClickListener(ItemToChat plugin) {
        super(plugin);
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
