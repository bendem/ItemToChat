package be.bendem.itemtochat.command.transaction;

import be.bendem.itemtochat.ItemToChat;
import be.bendem.itemtochat.Transaction;
import be.bendem.itemtochat.command.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collection;
import java.util.List;

/**
 * @author bendem
 */
public class TransactionListCommand extends AbstractTransactionCommand {

    public TransactionListCommand(ItemToChat plugin) {
        super(plugin);
    }

    @Override
    public void exec(CommandSender sender, List<String> args) {
        Player player = null;
        if(args.size() > 1) {
            player = Bukkit.getPlayerExact(args.get(1));
        }

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

}
