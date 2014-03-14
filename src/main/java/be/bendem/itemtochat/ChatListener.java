package be.bendem.itemtochat;

import be.bendem.itemtochat.jsonconverters.ItemStackConverter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * @author bendem
 */
public class ChatListener implements Listener {

    private final ItemToChat plugin;
    private String chatStringToReplace;

    public ChatListener(ItemToChat plugin) {
        this.plugin = plugin;
        chatStringToReplace = plugin.getConfig().getString("chat-string-to-replace", "[item]");
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        String message = e.getMessage();

        if(!message.contains(chatStringToReplace)
                || !e.getPlayer().hasPermission("itemtochat.chat.message")
                || e.getPlayer().getItemInHand() == null
                || e.getPlayer().getItemInHand().getType() == Material.AIR) {
            return;
        }

        String before = "<" + e.getPlayer().getDisplayName() + "> " + message.substring(0, message.indexOf(chatStringToReplace));
        String after = message.substring(message.indexOf(chatStringToReplace) + chatStringToReplace.length());
        ItemStackConverter itemStackConverter = new ItemStackConverter(plugin, e.getPlayer().getItemInHand(), before, after);
        for(Player p : e.getRecipients()) {
            plugin.logger.info("json : " + itemStackConverter.toString());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), itemStackConverter.toTellRawCommand(p.getName()));
        }

        e.setCancelled(true);
    }

}
