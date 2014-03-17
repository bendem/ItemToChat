package be.bendem.itemtochat.command.transaction;

import be.bendem.itemtochat.ItemToChat;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author bendem
 */
public class TransactionReloadCommand extends AbstractTransactionCommand {

    public TransactionReloadCommand(ItemToChat plugin) {
        super(plugin);
    }

    @Override
    public void exec(CommandSender sender, List<String> args) {
        plugin.getTransactionManager().loadTransactions();
        sendLogMessage(sender, "Transactions reloaded.", ChatColor.GREEN);
    }

}
