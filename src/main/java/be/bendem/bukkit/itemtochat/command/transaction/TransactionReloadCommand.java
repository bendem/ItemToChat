package be.bendem.bukkit.itemtochat.command.transaction;

import be.bendem.bukkit.itemtochat.ItemToChat;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author bendem
 */
public class TransactionReloadCommand extends AbstractTransactionCommand {

    public TransactionReloadCommand(ItemToChat plugin) {
        super(plugin, "itemtochat.commands.transaction.reload");
    }

    @Override
    public void exec(CommandSender sender, List<String> args) {
        plugin.getTransactionManager().loadTransactions();
        sendLogMessage(sender, "Transactions reloaded.", ChatColor.GREEN);
    }

}
