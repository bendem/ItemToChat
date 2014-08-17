package be.bendem.bukkit.itemtochat.command;

import be.bendem.bukkit.itemtochat.ItemToChat;
import be.bendem.bukkit.itemtochat.core.commands.AbstractCommand;
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
