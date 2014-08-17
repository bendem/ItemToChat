package be.bendem.bukkit.itemtochat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * @author bendem
 */
public class ChatListener implements Listener {

    private final ItemToChat plugin;

    public ChatListener(ItemToChat plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        // Permission?
        e.setCancelled(plugin.sendItem(e.getPlayer(), e.getRecipients(), e.getPlayer().getItemInHand(), e.getMessage()));
    }

}
