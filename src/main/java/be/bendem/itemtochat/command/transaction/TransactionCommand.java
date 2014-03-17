package be.bendem.itemtochat.command.transaction;

import be.bendem.itemtochat.ItemToChat;
import be.bendem.itemtochat.Transaction;
import be.bendem.itemtochat.command.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * @author bendem
 */
public class TransactionCommand extends AbstractCommand {

    private final HashMap<String, AbstractCommand> commandRegistry = new HashMap<>();

    public TransactionCommand(ItemToChat plugin) {
        super(plugin);

        commandRegistry.put("list", new TransactionListCommand());
    }

    @Override
    public void exec(CommandSender sender, List<String> args) {
        Action action;
        if(args.size() > 0) {
            action = Action.fromString(args.get(0));
            if(action == null) {
                action = Action.List;
            }
        } else {
            action = Action.List;
        }

        Player player = null;
        if(args.size() > 1) {
            player = Bukkit.getPlayerExact(args.get(1));
        }

        switch(action) {
            case List:
                list(sender, player);
                break;
            case Remove:
                remove(sender);
                break;
            case Reload:
                reload(sender);
                break;
            case Save:
                save(sender);
                break;
        }
    }

    private void save(CommandSender sender) {
        plugin.getTransactionManager().saveTransactions();
        sendLogMessage(sender, "Transaction saved.", ChatColor.GREEN);
    }

    private void remove(CommandSender sender) {
        // TODO Remove a specific transaction or all the transactions of a player
    }

    private void reload(CommandSender sender) {
        plugin.getTransactionManager().loadTransactions();
        sendLogMessage(sender, "Transactions reloaded.", ChatColor.GREEN);
    }

    private void list(CommandSender sender, Player player) {
        Collection<Transaction> transactions = plugin.getTransactionManager().getTransactions();
        if(transactions.isEmpty()) {
            sendLogMessage(sender, "No transactions...");
        }
        for(Transaction transaction : transactions) {
            if(player != null && !transaction.getSender().equalsIgnoreCase(player.getName())) {
                continue;
            }
            sendLogMessage(sender, "Transaction " + transaction.hashCode() + " : " + transaction.toString(), ChatColor.GRAY);
        }
    }

    @Override
    public boolean canBeUsedFromConsole() {
        return true;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission("itemtochat.commands.transaction");
    }

    public enum Action {
        List,
        Remove,
        Reload,
        Save;

        public static Action fromString(String str) {
            for(Action action : values()) {
                if(action.name().equalsIgnoreCase(str)) {
                    return action;
                }
            }
            return null;
        }
    }

}
