package be.bendem.itemtochat.command;

import be.bendem.itemtochat.ItemToChat;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Created by Ben on 11/03/14.
 */
abstract public class AbstractCommand {

    protected final ItemToChat plugin;

    public AbstractCommand(ItemToChat plugin) {
        this.plugin = plugin;
    }

    abstract public void exec(CommandSender sender, List<String> args);

    protected void sendLogMessage(CommandSender recipient, String message, ChatColor color) {
        recipient.sendMessage(ChatColor.GRAY + "[" + plugin.getName() + ": " + color + message + ChatColor.GRAY + "]");
    }

}
