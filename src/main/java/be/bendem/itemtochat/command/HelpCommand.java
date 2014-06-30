package be.bendem.itemtochat.command;

import be.bendem.itemtochat.ItemToChat;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author bendem
 */
public class HelpCommand extends AbstractCommand {

    public HelpCommand(ItemToChat plugin) {
        super(plugin, null);
    }

    @Override
    public void exec(CommandSender sender, List<String> args) {
        if(args.size() == 0) {
            sender.sendMessage(plugin.getCommand("itc").getUsage());
        }
    }

}
