package be.bendem.bukkit.itemtochat.command.transaction;

import be.bendem.bukkit.itemtochat.ItemToChat;
import be.bendem.bukkit.itemtochat.Transaction;
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
        super(plugin, "itemtochat.commands.transaction.list");
    }

    @Override
    public void exec(CommandSender sender, List<String> args) {
        Player player = null;
        if(args.size() > 1) {
            player = Bukkit.getPlayerExact(args.get(1));
        }

        Collection<Transaction> transactions = plugin.getTransactionManager().getTransactions();
        int transactionNb = 0;
        for(Transaction transaction : transactions) {
            if(player != null && !transaction.getSender().equals(player.getUniqueId()) || !transaction.isValid()) {
                continue;
            }
            sendLogMessage(sender, "Transaction " + transaction.hashCode() + " : " + transaction.toString(), ChatColor.GRAY);
            ++transactionNb;
        }
        if(transactionNb == 0) {
            sendLogMessage(sender, "No transactions...");
        } else {
            sendLogMessage(sender, "Total transactions : " + ChatColor.WHITE + transactionNb, ChatColor.GRAY);
        }
    }

}
