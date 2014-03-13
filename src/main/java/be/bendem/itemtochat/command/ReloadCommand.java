package be.bendem.itemtochat.command;

import be.bendem.itemtochat.ItemToChat;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author bendem
 */
public class ReloadCommand extends AbstractCommand {

    public ReloadCommand(ItemToChat plugin) {
        super(plugin);
    }

    @Override
    public void exec(CommandSender sender, List<String> args) {
        plugin.reloadConfig();
        sendLogMessage(sender, "Config reloaded.", ChatColor.GREEN);
    }

    @Override
    public boolean canBeUsedFromConsole() {
        return true;
    }

    @Override
    public boolean hasPermission(CommandSender sender) {
        return sender.hasPermission("itemtochat.commands.reload");
    }

}
