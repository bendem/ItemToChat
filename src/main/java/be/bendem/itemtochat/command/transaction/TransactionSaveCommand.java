package be.bendem.itemtochat.command.transaction;

import be.bendem.itemtochat.ItemToChat;
import be.bendem.itemtochat.command.AbstractCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author bendem
 */
public class TransactionSaveCommand extends AbstractCommand {

    public TransactionSaveCommand(ItemToChat plugin) {
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
        return false;
    }

}
