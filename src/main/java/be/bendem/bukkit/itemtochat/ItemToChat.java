package be.bendem.bukkit.itemtochat;

import be.bendem.bukkit.itemtochat.command.CommandHandler;
import mkremins.fanciful.FancyMessage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collection;
import java.util.logging.Logger;

/**
 * ItemToChat for Bukkit
 *
 * @author bendem
 */
public class ItemToChat extends JavaPlugin {

    public  Logger             logger;
    private TransactionManager transactionManager;
    private BukkitTask         autosaveTask;
    private String chatStringToReplace;

    @Override
    public void onEnable() {
        // TODO Make the plugin creative friendly (at the moment, middle click is not detected on creative)
        logger = getLogger();
        saveDefaultConfig();

        transactionManager = new TransactionManager(this);
        transactionManager.loadTransactions();

        // Start autosave
        long autosaveDelay = Utils.secondsToTicks(getConfig().getLong("autosave-delay", 300));
        autosaveTask = Bukkit.getScheduler().runTaskTimerAsynchronously(this, new Runnable() {
            @Override
            public void run() {
                transactionManager.saveTransactions();
            }
        }, autosaveDelay, autosaveDelay);
        if(autosaveTask.getTaskId() == -1) {
            throw new RuntimeException("Could not start autosave task!");
        }

        getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);

        getCommand("itc").setExecutor(new CommandHandler(this));

        chatStringToReplace = getConfig().getString("chat-string-to-replace", "[item]");
    }

    @Override
    public void onDisable() {
        autosaveTask.cancel();
        transactionManager.saveTransactions();
    }

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public boolean sendItem(Player from, Player to, ItemStack item, String message) {
        FancyMessage fancyMessage = getMessage(from, item, message);
        if(fancyMessage != null) {
            fancyMessage.send(to);
        }
        return fancyMessage != null;
    }

    public boolean sendItem(Player from, Collection<Player> to, ItemStack item, String message) {
        FancyMessage fancyMessage = getMessage(from, item, message);
        if(fancyMessage != null) {
            fancyMessage.send(to);
        }
        return fancyMessage != null;
    }

    public boolean sendItem(Player from, ItemStack item, String message) {
        FancyMessage fancyMessage = getMessage(from, item, message);
        if(fancyMessage != null) {
            fancyMessage.send(Bukkit.getOnlinePlayers());
        }
        return fancyMessage != null;
    }

    private FancyMessage getMessage(Player from, ItemStack item, String message) {
        if(!message.contains(chatStringToReplace)
                || !from.hasPermission("itemtochat.chat.message")
                || item == null
                || item.getType() == Material.AIR) {
            return null;
        }

        return new FancyMessage("<")
                .then(from.getDisplayName())
                .then("> ")
                .then(message.substring(0, message.indexOf(chatStringToReplace)))
                .then("[")
                .then()
                    .color(getItemColor(item))
                    .text(StringUtils.capitalize(item.getType().name().toLowerCase().replace("_", " ")))
                    .itemTooltip(item)
                .then()
                    .text(" x " + item.getAmount())
                    .itemTooltip(item)
                .then("]")
                .then(message.substring(message.indexOf(chatStringToReplace) + chatStringToReplace.length()));
    }

    private ChatColor getItemColor(ItemStack item) {
        // TODO handle wool / stained glass / leather armor (using autodetect-colors)
        return ChatColor.valueOf(getConfig().getString("item-colors." + item.getType().name().toLowerCase(), "white").toUpperCase());
    }

}
