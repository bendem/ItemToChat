package be.bendem.itemtochat;

import be.bendem.itemtochat.jsonconverters.ItemStackConverter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Created by Ben on 9/03/14.
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
        String json = new ItemStackConverter(e.getPlayer().getItemInHand(), plugin, before, after).toJson();
        for(Player p : e.getRecipients()) {
            plugin.logger.info("command : " + ItemStackConverter.toTellRawCommand(p.getName(), json));
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ItemStackConverter.toTellRawCommand(p.getName(), json));
        }

        e.setCancelled(true);
    }

}
