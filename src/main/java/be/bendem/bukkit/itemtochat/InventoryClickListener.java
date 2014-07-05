package be.bendem.bukkit.itemtochat;

import be.bendem.bukkit.itemtochat.jsonconverters.ItemStackConverter;
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

        ItemStackConverter itemStackConverter = new ItemStackConverter(plugin, e.getCurrentItem(), "<" + ((Player) e.getWhoClicked()).getDisplayName() + "> ");

        plugin.logger.info("json: " + itemStackConverter.toString());

        // TODO Permission check for sender and receivers
        ItemToChat.dispatchCommand(null, itemStackConverter);
    }

}