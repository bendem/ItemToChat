package be.bendem.bukkit.itemtochat.command;

import be.bendem.bukkit.itemtochat.ItemToChat;
import be.bendem.bukkit.itemtochat.Transaction;
import be.bendem.bukkit.itemtochat.jsonconverters.ItemStackConverter;
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
        String sendTo = null;
        if(args.size() == 1) {
            Player p = Bukkit.getPlayerExact(args.get(0));
            if(p == null) {
                sendLogMessage(sender, args.get(0) + " is not connected...");
                return;
            }
            sendTo = p.getName();
        }

        Player player = (Player) sender;

        // TODO Message from config
        ItemStackConverter converter = new ItemStackConverter(plugin, player.getItemInHand(), player.getName() + " is sending ");
        int transacNumber = plugin.getTransactionManager().add(new Transaction(player.getUniqueId(), player.getItemInHand(), new Date().getTime(), 60_000)); // TODO Lifetime from config!
        plugin.logger.info("Transaction created : " + transacNumber);
        ItemToChat.dispatchCommand(sendTo, converter);
    }

}