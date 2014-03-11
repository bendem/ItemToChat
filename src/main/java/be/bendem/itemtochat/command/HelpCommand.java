package be.bendem.itemtochat.command;

import be.bendem.itemtochat.ItemToChat;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * Created by Ben on 11/03/14.
 */
public class HelpCommand extends AbstractCommand {

    public HelpCommand(ItemToChat plugin) {
        super(plugin);
    }

    @Override
    public void exec(CommandSender sender, List<String> args) {
    }

}
