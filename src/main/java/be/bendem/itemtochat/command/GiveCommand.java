package be.bendem.itemtochat.command;

import be.bendem.itemtochat.ItemToChat;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author bendem
 */
public class GiveCommand extends AbstractCommand {

    public GiveCommand(ItemToChat plugin) {
        super(plugin, "itemtochat.commands.give", false);
    }

    @Override
    public void exec(CommandSender sender, List<String> args) {
    }

}
