package be.bendem.bukkit.itemtochat.events;

import be.bendem.bukkit.itemtochat.ItemToChat;
import be.bendem.bukkit.itemtochat.core.BaseListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * @author bendem
 */
public class ChatListener extends BaseListener {

    private final ItemToChat plugin;

    public ChatListener(ItemToChat plugin) {
        super(plugin);
        this.plugin = plugin;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        // Permission?
        e.setCancelled(plugin.sendItem(e.getPlayer(), e.getRecipients(), e.getPlayer().getItemInHand(), e.getMessage()));
    }

}
