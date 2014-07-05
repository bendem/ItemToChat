package be.bendem.bukkit.itemtochat.command.transaction;

import be.bendem.bukkit.itemtochat.ItemToChat;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author bendem
 */
public class TransactionSaveCommand extends AbstractTransactionCommand {

    public TransactionSaveCommand(ItemToChat plugin) {
        super(plugin, "itemtochat.commands.transaction.save");
    }

    @Override
    public void exec(CommandSender sender, List<String> args) {
        plugin.getTransactionManager().saveTransactions();
        sendLogMessage(sender, "Transaction saved.", ChatColor.GREEN);
    }

}
