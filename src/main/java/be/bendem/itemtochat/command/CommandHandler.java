package be.bendem.itemtochat.command;

import be.bendem.itemtochat.ItemToChat;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Ben on 9/03/14.
 */
public class CommandHandler implements CommandExecutor {

    private final ItemToChat                       plugin;
    private final HashMap<String, AbstractCommand> commandRegistry;
    private final HashMap<String, String>          aliases;

    public CommandHandler(ItemToChat plugin) {
        this.plugin = plugin;

        aliases = new HashMap<>();
        aliases.put("sh", "show");
        aliases.put("s", "send");
        aliases.put("g", "give");

        commandRegistry = new HashMap<>();
        commandRegistry.put("show", new ShowCommand(plugin));
        commandRegistry.put("send", new SendCommand(plugin));
        commandRegistry.put("give", new GiveCommand(plugin));
        commandRegistry.put("help", new HelpCommand(plugin));
        commandRegistry.put("reload", new ReloadCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        // TODO See https://github.com/KittehOrg/Pakkit/blob/master/src/main/java/org/kitteh/pakkit/AbstractCommand.java
        // TODO See https://github.com/Ribesg/NPlugins/blob/master/NCuboid/src/main/java/fr/ribesg/bukkit/ncuboid/commands/MainCommandExecutor.java#L76
        if(args.length == 0 || !command.getName().equalsIgnoreCase("itc")) {
            return false;
        }
        String commandName = args[0];
        if(aliases.containsKey(commandName)) {
            commandName = aliases.get(commandName);
        }
        if(commandRegistry.containsKey(commandName)) {
            commandRegistry.get(commandName).exec(sender, Arrays.asList(args).subList(1, args.length));
            return true;
        }
        return false;
        /*
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
        return false;*/
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
