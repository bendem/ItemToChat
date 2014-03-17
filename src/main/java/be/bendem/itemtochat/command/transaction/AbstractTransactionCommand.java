package be.bendem.itemtochat.command.transaction;

import be.bendem.itemtochat.ItemToChat;
import be.bendem.itemtochat.command.AbstractCommand;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author bendem
 */
public abstract class AbstractTransactionCommand extends AbstractCommand {

    public AbstractTransactionCommand(ItemToChat plugin) {
        super(plugin);
    }

    @Override
    public boolean canBeUsedFromConsole() {
        return true;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission("itemtochat.commands.transaction");
    }
}
