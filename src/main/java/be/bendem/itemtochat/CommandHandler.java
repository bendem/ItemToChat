package be.bendem.itemtochat;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ben on 9/03/14.
 */
public class CommandHandler implements CommandExecutor {

    private final ItemToChat plugin;

    public CommandHandler(ItemToChat plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // TODO See https://github.com/KittehOrg/Pakkit/blob/master/src/main/java/org/kitteh/pakkit/Command.java
        // TODO See https://github.com/Ribesg/NPlugins/blob/master/NCuboid/src/main/java/fr/ribesg/bukkit/ncuboid/commands/MainCommandExecutor.java#L76
        if(args.length == 0) {
            return false;
        }
        if(args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sendLogMessage(sender, "Config reloaded.", ChatColor.GREEN);
            return true;
        }
        if(sender instanceof ConsoleCommandSender) {
            sendLogMessage(sender, "You can only reload the config from the console!", ChatColor.RED);
            return true;
        }
        if(!checkItemInHand(sender)) {
            return true;
        }
        if(args[0].equalsIgnoreCase("show")) {
            return show(sender, Arrays.asList(args).subList(1, args.length));
        }
        if(args[0].equalsIgnoreCase("send")) {
            return send(sender, Arrays.asList(args).subList(1, args.length));
        }
        if(args[0].equalsIgnoreCase("give")) {
            return give(sender, Arrays.asList(args).subList(1, args.length));
        }
        return false;
    }

    private boolean show(CommandSender sender, List<String> args) {
        return false;
    }

    private boolean send(CommandSender sender, List<String> args) {
        return false;
    }

    private boolean give(CommandSender sender, List<String> args) {
        return false;
    }

    private boolean checkItemInHand(CommandSender sender) {
        Player player = (Player) sender;
        if(player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
            sendLogMessage(sender, "You should have a valid item in your hand!", ChatColor.RED);
            return false;
        }
        return true;
    }

    private void sendLogMessage(CommandSender recipient, String message, ChatColor color) {
        recipient.sendMessage(ChatColor.GRAY + "[" + plugin.getName() + ": " + color + message + ChatColor.GRAY + "]");
    }

}
