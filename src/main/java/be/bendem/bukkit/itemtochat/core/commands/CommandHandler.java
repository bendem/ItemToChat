package be.bendem.bukkit.itemtochat.core.commands;

import be.bendem.bukkit.itemtochat.ItemToChat;
import be.bendem.bukkit.itemtochat.command.GiveCommand;
import be.bendem.bukkit.itemtochat.command.HelpCommand;
import be.bendem.bukkit.itemtochat.command.InternalCommand;
import be.bendem.bukkit.itemtochat.command.ReloadCommand;
import be.bendem.bukkit.itemtochat.command.SendCommand;
import be.bendem.bukkit.itemtochat.command.ShowCommand;
import be.bendem.bukkit.itemtochat.command.transaction.TransactionCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bendem
 */
public class CommandHandler implements CommandExecutor {

    private final Map<String, AbstractCommand> commands;
    private final Map<String, String>          aliases;

    public CommandHandler(ItemToChat plugin) {
        plugin.getCommand("itc").setExecutor(this);

        aliases = new HashMap<>();
        aliases.put("sh", "show");
        aliases.put("s", "send");
        aliases.put("g", "give");
        aliases.put("t", "transaction");

        commands = new HashMap<>();
        commands.put("show", new ShowCommand(plugin));
        commands.put("send", new SendCommand(plugin));
        commands.put("give", new GiveCommand(plugin));
        commands.put("help", new HelpCommand(plugin));
        commands.put("reload", new ReloadCommand(plugin));
        commands.put("transaction", new TransactionCommand(plugin));
        commands.put("internal", new InternalCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0 || !command.getName().equalsIgnoreCase("itc")) {
            return false;
        }
        String commandName = args[0].toLowerCase();
        if(aliases.containsKey(commandName)) {
            commandName = aliases.get(commandName);
        }
        if(commands.containsKey(commandName)) {
            final AbstractCommand c = commands.get(commandName);

            if(!c.hasPermission(sender)) {
                c.sendLogMessage(sender, "You don't have the permission to use that command.");
            } else if(sender instanceof ConsoleCommandSender && !c.canBeUsedFromConsole()) {
                c.sendLogMessage(sender, "You can't use this command from the console.");
            } else {
                c.exec(sender, Arrays.asList(args).subList(1, args.length));
            }

            return true;
        }
        return false;
    }

}
