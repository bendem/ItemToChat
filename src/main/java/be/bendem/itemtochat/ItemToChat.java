package be.bendem.itemtochat;

import be.bendem.itemtochat.command.CommandHandler;
import be.bendem.itemtochat.jsonconverters.ItemStackConverter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * ItemToChat for Bukkit
 *
 * @author bendem
 */
public class ItemToChat extends JavaPlugin {

    public  Logger             logger;
    private TransactionManager transactionManager;

    @Override
    public void onEnable() {
        // TODO Make the plugin creative friendly (at the moment, middle click is not detected on creative)
        logger = getLogger();
        saveDefaultConfig();

        transactionManager = new TransactionManager(this);
        transactionManager.loadTransactions();

        getServer().getPluginManager().registerEvents(new InventoryClickListener(this), this);
        getServer().getPluginManager().registerEvents(new ChatListener(this), this);

        getCommand("itc").setExecutor(new CommandHandler(this));
    }

    @Override
    public void onDisable() {
        transactionManager.saveTransactions();
    }

    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    public static void dispatchCommand(String to, ItemStackConverter converter) {
        if(to == null) {
            for(Player p : Bukkit.getOnlinePlayers()) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), converter.toTellRawCommand(p.getName()));
            }
        } else {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), converter.toTellRawCommand(to));
        }
    }

}
