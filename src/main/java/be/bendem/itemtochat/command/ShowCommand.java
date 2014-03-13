package be.bendem.itemtochat.command;

import be.bendem.itemtochat.ItemToChat;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author bendem
 */
public class ShowCommand extends AbstractCommand {

    public ShowCommand(ItemToChat plugin) {
        super(plugin);
    }

    @Override
    public void exec(CommandSender sender, List<String> args) {
    }

    @Override
    public boolean canBeUsedFromConsole() {
        return false;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission("itemtochat.commands.show");
    }

}
