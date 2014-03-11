package be.bendem.itemtochat.command;

import be.bendem.itemtochat.ItemToChat;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
    abstract public boolean canBeUsedFromConsole();
    abstract public boolean hasPermission(CommandSender sender);

    public void sendLogMessage(CommandSender recipient, String message) {
        sendLogMessage(recipient, message, ChatColor.RED);
    }

    public void sendLogMessage(CommandSender recipient, String message, ChatColor color) {
        recipient.sendMessage(ChatColor.GRAY + "[" + plugin.getName() + ": " + color + message + ChatColor.GRAY + "]");
    }

    protected boolean checkItemInHand(CommandSender sender) {
        Player player = (Player) sender;
        if(player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
            sendLogMessage(sender, "You should have a valid item in your hand!", ChatColor.RED);
            return false;
        }
        return true;
    }

}
