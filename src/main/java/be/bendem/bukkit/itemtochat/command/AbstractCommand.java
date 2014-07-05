package be.bendem.bukkit.itemtochat.command;

import be.bendem.bukkit.itemtochat.ItemToChat;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * @author bendem
 */
abstract public class AbstractCommand {

    protected final ItemToChat plugin;
    protected final String permission;
    protected final boolean consoleUse;

    public AbstractCommand(ItemToChat plugin, String permission) {
        this(plugin, permission, true);
    }

    public AbstractCommand(ItemToChat plugin, String permission, boolean consoleUse) {
        this.plugin = plugin;
        this.permission = permission;
        this.consoleUse = consoleUse;
    }

    abstract public void exec(CommandSender sender, List<String> args);

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

    public boolean hasPermission(CommandSender sender) {
        return permission == null || sender.hasPermission(permission);
    }

    public boolean canBeUsedFromConsole() {
        return consoleUse;
    }

}
