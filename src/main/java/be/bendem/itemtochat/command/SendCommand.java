package be.bendem.itemtochat.command;

import be.bendem.itemtochat.ItemToChat;
import be.bendem.itemtochat.Transaction;
import be.bendem.itemtochat.jsonconverters.ItemStackConverter;
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
        super(plugin);
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
        int transacNumber = plugin.getTransactionManager().add(new Transaction(player.getName(), player.getItemInHand(), new Date().getTime(), 60_000)); // TODO Lifetime from config!
        plugin.logger.info("Transaction created : " + transacNumber);
        ItemToChat.dispatchCommand(sendTo, converter);
    }

    @Override
    public boolean canBeUsedFromConsole() {
        return false;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission("itemtochat.commands.send");
    }

}
