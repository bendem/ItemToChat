package be.bendem.itemtochat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

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

        String json = new ItemStackToTellRaw(e.getCurrentItem(), plugin).toJson();

        plugin.logger.info(json);

        // TODO Change this to send message to all players (@a is not recognized by bukkit), from the console
        plugin.getServer().dispatchCommand(plugin.getServer().getPlayerExact("bendembd"),
            "tellraw bendembd { text: \"\", extra: " + json + "}");
    }

}
