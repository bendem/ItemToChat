package be.bendem.bukkit.itemtochat.command.transaction;

import be.bendem.bukkit.itemtochat.ItemToChat;
import be.bendem.bukkit.itemtochat.core.commands.AbstractCommand;

/**
 * @author bendem
 */
public abstract class AbstractTransactionCommand extends AbstractCommand {

    public AbstractTransactionCommand(ItemToChat plugin, String permission) {
        super(plugin, permission);
    }

}
