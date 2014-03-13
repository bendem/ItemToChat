package be.bendem.itemtochat.command;

import be.bendem.itemtochat.ItemToChat;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import java.util.Arrays;
import java.util.HashMap;

/**
 * @author bendem
 */
public class CommandHandler implements CommandExecutor {

    private final ItemToChat                       plugin;
    private final HashMap<String, AbstractCommand> commandRegistry;
    private final HashMap<String, String>          aliases;

    public CommandHandler(ItemToChat plugin) {
        this.plugin = plugin;

        aliases = new HashMap<>();
        aliases.put("sh", "show");
        aliases.put("s", "send");
        aliases.put("g", "give");

        commandRegistry = new HashMap<>();
        commandRegistry.put("show", new ShowCommand(plugin));
        commandRegistry.put("send", new SendCommand(plugin));
        commandRegistry.put("give", new GiveCommand(plugin));
        commandRegistry.put("help", new HelpCommand(plugin));
        commandRegistry.put("reload", new ReloadCommand(plugin));
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        // TODO See https://github.com/KittehOrg/Pakkit/blob/master/src/main/java/org/kitteh/pakkit/AbstractCommand.java
        // TODO See https://github.com/Ribesg/NPlugins/blob/master/NCuboid/src/main/java/fr/ribesg/bukkit/ncuboid/commands/MainCommandExecutor.java#L76
        if(args.length == 0 || !command.getName().equalsIgnoreCase("itc")) {
            return false;
        }
        String commandName = args[0].toLowerCase();
        if(aliases.containsKey(commandName)) {
            commandName = aliases.get(commandName);
        }
        if(commandRegistry.containsKey(commandName)) {
            final AbstractCommand c = commandRegistry.get(commandName);

            if(!c.hasPermission(sender)) {
                c.sendLogMessage(sender, "You don't have the permission to use that command.");
            }else if(sender instanceof ConsoleCommandSender && !c.canBeUsedFromConsole()) {
                c.sendLogMessage(sender, "You can't use this command from the console.");
            } else {
                c.exec(sender, Arrays.asList(args).subList(1, args.length));
            }

            return true;
        }
        return false;
    }

}
