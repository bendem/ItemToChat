package be.bendem.bukkit.itemtochat.command;

import be.bendem.bukkit.itemtochat.ItemToChat;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author bendem
 */
public class ShowCommand extends AbstractCommand {

    public ShowCommand(ItemToChat plugin) {
        super(plugin, "itemtochat.commands.show", false);
    }

    @Override
    public void exec(CommandSender sender, List<String> args) {
    }

}
