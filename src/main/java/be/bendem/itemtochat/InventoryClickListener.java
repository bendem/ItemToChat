package be.bendem.itemtochat;

import be.bendem.itemtochat.jsonconverters.ItemStackConverter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.Listener;
import org.bukkit.Material;

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

        plugin.logger.info("===============================");
        plugin.logger.info("Click!");

        String json = new ItemStackConverter(e.getCurrentItem(), plugin, "<" + ((Player) e.getWhoClicked()).getDisplayName() + "> ").toJson();

        plugin.logger.info(json);

        // TODO Permission check for sender and receivers
        for(Player p : Bukkit.getOnlinePlayers()) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ItemStackConverter.toTellRawCommand(p.getName(), json));
        }
    }

}
