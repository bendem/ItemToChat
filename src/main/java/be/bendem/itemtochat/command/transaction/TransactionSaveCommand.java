package be.bendem.itemtochat.command.transaction;

import be.bendem.itemtochat.ItemToChat;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author bendem
 */
public class TransactionSaveCommand extends AbstractTransactionCommand {

    public TransactionSaveCommand(ItemToChat plugin) {
        super(plugin);
    }

    @Override
    public void exec(CommandSender sender, List<String> args) {
        plugin.getTransactionManager().saveTransactions();
        sendLogMessage(sender, "Transaction saved.", ChatColor.GREEN);
    }

}
