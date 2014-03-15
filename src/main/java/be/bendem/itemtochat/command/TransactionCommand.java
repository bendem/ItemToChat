package be.bendem.itemtochat.command;

import be.bendem.itemtochat.ItemToChat;
import be.bendem.itemtochat.Transaction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * @author bendem
 */
public class TransactionCommand extends AbstractCommand {

    public TransactionCommand(ItemToChat plugin) {
        super(plugin);
    }

    @Override
    public void exec(CommandSender sender, List<String> args) {
        Action action = Action.List;
        if(args.size() > 0) {
            action = Action.valueOf(args.get(0));
        }
        Player player = null;
        if(args.size() > 1) {
            player = Bukkit.getPlayerExact(args.get(1));
        }

        if(action == Action.List) {
            list(sender, player);
        }
    }

    private void list(CommandSender sender, Player player) {
        for(Transaction transaction : plugin.getTransactionManager().getTransactions()) {
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
        Remove
    }

}
