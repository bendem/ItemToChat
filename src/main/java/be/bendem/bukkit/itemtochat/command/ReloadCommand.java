package be.bendem.bukkit.itemtochat.command;

import be.bendem.bukkit.itemtochat.ItemToChat;
import be.bendem.bukkit.itemtochat.core.commands.AbstractCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author bendem
 */
public class ReloadCommand extends AbstractCommand {

    public ReloadCommand(ItemToChat plugin) {
        super(plugin, "itemtochat.commands.reload");
    }

    @Override
    public void exec(CommandSender sender, List<String> args) {
        plugin.reloadConfig();
        sendLogMessage(sender, "Config reloaded.", ChatColor.GREEN);
    }

}
