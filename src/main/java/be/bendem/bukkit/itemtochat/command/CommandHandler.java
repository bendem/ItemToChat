package be.bendem.bukkit.itemtochat.command;

import be.bendem.bukkit.itemtochat.ItemToChat;
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

    private final ItemToChat                   plugin;
    private final Map<String, AbstractCommand> commandsRegistry;
    private final Map<String, String>          aliasesRegistry;

    public CommandHandler(ItemToChat plugin) {
        this.plugin = plugin;

        aliasesRegistry = new HashMap<>();
        aliasesRegistry.put("sh", "show");
        aliasesRegistry.put("s", "send");
        aliasesRegistry.put("g", "give");
        aliasesRegistry.put("t", "transaction");

        commandsRegistry = new HashMap<>();
        commandsRegistry.put("show", new ShowCommand(plugin));
        commandsRegistry.put("send", new SendCommand(plugin));
        commandsRegistry.put("give", new GiveCommand(plugin));
        commandsRegistry.put("help", new HelpCommand(plugin));
        commandsRegistry.put("reload", new ReloadCommand(plugin));
        commandsRegistry.put("transaction", new TransactionCommand(plugin));
        commandsRegistry.put("internal", new InternalCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0 || !command.getName().equalsIgnoreCase("itc")) {
            return false;
        }
        String commandName = args[0].toLowerCase();
        if(aliasesRegistry.containsKey(commandName)) {
            commandName = aliasesRegistry.get(commandName);
        }
        if(commandsRegistry.containsKey(commandName)) {
            final AbstractCommand c = commandsRegistry.get(commandName);

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
