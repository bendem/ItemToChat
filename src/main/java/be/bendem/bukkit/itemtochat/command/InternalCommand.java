package be.bendem.bukkit.itemtochat.command;

import be.bendem.bukkit.itemtochat.ItemToChat;
import be.bendem.bukkit.itemtochat.Transaction;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Date;
import java.util.List;

/**
 * @author bendem
 */
public class InternalCommand extends AbstractCommand {

    public InternalCommand(ItemToChat plugin) {
        super(plugin, "itemtochat.chat.click", false);
    }

    @Override
    public void exec(CommandSender sender, List<String> args) {
        int identifier = isValid(sender, args);
        if(identifier == 0) {
            return;
        }

        Transaction transaction = plugin.getTransactionManager().get(identifier);
        plugin.logger.info("Time      : " + new Date().getTime());
        plugin.logger.info("TimeStamp : " + transaction.getTimeStamp());
        plugin.logger.info("LifeTime  : " + transaction.getLifeTime());
        plugin.logger.info("remaining : " + (new Date().getTime() - transaction.getTimeStamp() - transaction.getLifeTime()));
        if(!transaction.isValid()) {
            plugin.getTransactionManager().remove(transaction);
            sendLogMessage(sender, "This transaction has expired...");
        }
        if(transaction.getType() == Transaction.Type.Send) {
            trade(Bukkit.getPlayer(transaction.getSender()), (Player) sender, transaction.getItemStack());
        } else {
            give((Player) sender, transaction.getItemStack());
        }
    }

    private void give(Player receiver, ItemStack itemStack) {
        // TODO Give itemstack to receiver
    }

    private void trade(Player sender, Player receiver, ItemStack itemStack) {
        // TODO Take from sender, give to receiver if could be taken from sender
    }

    private int isValid(CommandSender sender, List<String> args) {
        if(args.size() != 1) {
            sendLogMessage(sender, "Don't mess with this command!");
            return 0;
        }
        int identifier;
        try {
            identifier = Integer.parseInt(args.get(0));
        } catch(NumberFormatException e) {
            sendLogMessage(sender, "Don't mess with this command!");
            return 0;
        }
        if(!plugin.getTransactionManager().contains(identifier)) {
            sendLogMessage(sender, "This transaction does not exists anymore...", ChatColor.GRAY);
            return 0;
        }
        return identifier;
    }

}
