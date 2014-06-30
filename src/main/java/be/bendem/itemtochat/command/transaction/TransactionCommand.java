package be.bendem.itemtochat.command.transaction;

import be.bendem.itemtochat.ItemToChat;
import be.bendem.itemtochat.command.AbstractCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.HashMap;
import java.util.List;

/**
 * @author bendem
 */
public class TransactionCommand extends AbstractCommand {

    private final HashMap<String, AbstractCommand> commandRegistry = new HashMap<>();

    public TransactionCommand(ItemToChat plugin) {
        super(plugin, "itemtochat.commands.transaction");

        commandRegistry.put("list", new TransactionListCommand(plugin));
        commandRegistry.put("save", new TransactionSaveCommand(plugin));
        commandRegistry.put("reload", new TransactionReloadCommand(plugin));
    }

    @Override
    public void exec(CommandSender sender, List<String> args) {
        if(args.size() == 0) {
            sendLogMessage(sender, "Your command is incomplete, type /itc help to find out why...");
            return;
        }

        if(commandRegistry.containsKey(args.get(0))) {
            final AbstractCommand c = commandRegistry.get(args.get(0));

            if(!c.hasPermission(sender)) {
                c.sendLogMessage(sender, "You don't have the permission to use that command.");
            } else if(sender instanceof ConsoleCommandSender && !c.canBeUsedFromConsole()) {
                c.sendLogMessage(sender, "You can't use this command from the console.");
            } else {
                c.exec(sender, args.subList(1, args.size()));
                return;
            }
        }
        sendLogMessage(sender, "Invalid command, type /itc help to find out why...");
    }

    private void remove(CommandSender sender) {
        // TODO Remove a specific transaction or all the transactions of a player
    }

}
