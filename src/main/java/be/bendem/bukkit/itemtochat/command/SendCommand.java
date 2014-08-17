package be.bendem.bukkit.itemtochat.command;

import be.bendem.bukkit.itemtochat.ItemToChat;
import be.bendem.bukkit.itemtochat.Transaction;
import be.bendem.bukkit.itemtochat.core.commands.AbstractCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Date;
import java.util.List;

/**
 * @author bendem
 */
public class SendCommand extends AbstractCommand {

    public SendCommand(ItemToChat plugin) {
        super(plugin, "itemtochat.commands.send", false);
    }

    @Override
    public void exec(CommandSender sender, List<String> args) {
        if(!checkItemInHand(sender)) {
            return;
        }
        if(args.size() > 1) {
            sendLogMessage(sender, "Too much arguments...");
        }
        Player sendTo = null;
        if(args.size() == 1) {
            sendTo = Bukkit.getPlayerExact(args.get(0));
            if(sendTo == null) {
                sendLogMessage(sender, args.get(0) + " is not connected...");
                return;
            }
        }

        Player player = (Player) sender;

        // TODO Message from config
        // TODO Lifetime from config!
        int transacNumber = plugin.getTransactionManager().add(new Transaction(player.getUniqueId(), player.getItemInHand(), new Date().getTime(), 60_000));
        plugin.logger.info("Transaction created : " + transacNumber);
        // TODO Fix that message to be a /me
        if(sendTo == null) {
            plugin.sendItem(player, player.getItemInHand(), " is sending [item].");
        } else {
            plugin.sendItem(player, sendTo, player.getItemInHand(), " is sending [item].");
        }
    }

}
