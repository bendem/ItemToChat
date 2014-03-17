package be.bendem.itemtochat;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bendem
 */
public class TransactionManager {

    public static final String FILENAME = "transactions.yml";

    private final ItemToChat           plugin;
    private final HashMap<Integer, Transaction> transactions;
    private final File                 file;

    public TransactionManager(ItemToChat plugin) {
        this.plugin = plugin;
        this.transactions = new HashMap<>();

        file = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + FILENAME);
        createFile(false);
    }

    public void loadTransactions() {
        ConfigurationSection config = YamlConfiguration.loadConfiguration(file).getConfigurationSection("transactions");
        if(config == null) {
            plugin.logger.info("File ignored, no transactions found...");
            return;
        }

        for(String key : config.getKeys(false)) {
            plugin.logger.info("Loading transaction : " + key);
            add(Transaction.deserialize(config.getConfigurationSection(key)));
        }
    }

    public void saveTransactions() {
        YamlConfiguration config = new YamlConfiguration();
        config.options().header("Do not modify this file except if you want your house to be destroyed by angry galactic camels!").copyHeader(true);

        for(Transaction transaction: transactions.values()) {
            // TODO  Check why hashCode don't always return the same value :/
            config.set("transactions." + transaction.hashCode(), Transaction.serialize(transaction));
        }

        try {
            config.save(file);
        } catch(IOException e) {
            plugin.logger.warning("Could not save transactions!");
        }
    }

    public int add(Transaction transaction) {
        transactions.put(transaction.hashCode(), transaction);
        return transaction.hashCode();
    }

    public void remove(Transaction transaction) {
        if(contains(transaction)) {
            transactions.remove(transaction);
        }
    }

    public boolean contains(Transaction transaction) {
        return transactions.containsValue(transaction);
    }

    public boolean contains(int transactionHash) {
        return transactions.containsKey(transactionHash);
    }

    public Transaction get(int transactionHash) {
        return transactions.get(transactionHash);
    }

    public Collection<Transaction> getTransactions() {
        return transactions.values();
    }

    private void createFile(boolean replace) {
        if(!file.exists() || replace) {
            try {
                file.createNewFile();
            } catch(IOException e) {
                plugin.logger.warning("Could not create transaction file!");
            }
        }
    }

}
