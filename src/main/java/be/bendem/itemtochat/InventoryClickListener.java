package be.bendem.itemtochat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.Listener;
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

        // FIXME At the moment, we usurpate an op identity to send the tellraw command from. This is not the way to go!
        Player firstOp = null;
        for(OfflinePlayer op : Bukkit.getOperators()) {
            if(op.isOnline()) {
                firstOp = op.getPlayer();
            }
        }
        if(firstOp == null) {
            ((Player) e.getWhoClicked()).sendMessage(ChatColor.RED + "No operator, can't use the tellraw command...");
            return;
        }
        for(Player p : Bukkit.getOnlinePlayers()) {
            Bukkit.dispatchCommand(firstOp, "tellraw " + p.getName() + " { text: \"\", extra: " + json + "}");
        }
    }

}
