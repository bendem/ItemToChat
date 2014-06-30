package be.bendem.itemtochat.command.transaction;

import be.bendem.itemtochat.ItemToChat;
import be.bendem.itemtochat.command.AbstractCommand;

/**
 * @author bendem
 */
public abstract class AbstractTransactionCommand extends AbstractCommand {

    public AbstractTransactionCommand(ItemToChat plugin, String permission) {
        super(plugin, permission);
    }

}
