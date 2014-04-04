package be.bendem.itemtochat;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author bendem
 */
public class TransactionManager {

    public static final String FILENAME = "transactions.yml";

    private final ItemToChat                    plugin;
    private final HashMap<Integer, Transaction> transactions;
    private final File                          file;

    public TransactionManager(ItemToChat plugin) {
        this.plugin = plugin;
        this.transactions = new HashMap<>();

        file = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + FILENAME);
        createFile(false);
    }

    /**
     * Load all the transactions from the file and clean the Map of the unvalid transactions
     */
    public synchronized void loadTransactions() {
        ConfigurationSection config = YamlConfiguration.loadConfiguration(file).getConfigurationSection("transactions");
        if(config == null) {
            plugin.logger.info("File ignored, no transactions found...");
            return;
        }

        for(String key : config.getKeys(false)) {
            plugin.logger.info("Loading transaction : " + key);
            add(Transaction.deserialize(config.getConfigurationSection(key)));
        }
        clean();
    }

    /**
     * Save all the transactions to the file (the transactions in the file are lost)
     */
    public synchronized void saveTransactions() {
        YamlConfiguration config = new YamlConfiguration();
        config.options().header("Do not modify this file unless you want your house to be destroyed by angry galactic camels!").copyHeader(true);

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

    /**
     * Remove all invalid transactions from the Map
     */
    public synchronized void clean() {
        for(Map.Entry<Integer, Transaction> entry : transactions.entrySet()) {
            if(!entry.getValue().isValid()) {
                transactions.remove(entry.getKey());
            }
        }
    }

    /**
     * Add a transaction to the Map
     * @return The hash of the map or 0 if it hasn't been inserted
     */
    public synchronized int add(Transaction transaction) {
        if(transaction.isValid() && !contains(transaction)) {
            transactions.put(transaction.hashCode(), transaction);
            return transaction.hashCode();
        }
        return 0;
    }

    /**
     * Remove a specific transaction
     * @param transaction Transaction to remove
     */
    public synchronized void remove(Transaction transaction) {
        remove(transaction.hashCode());
    }

    /**
     * Remove a specific transaction
     * @param transactionHash Hash code of the transaction
     */
    public synchronized void remove(int transactionHash) {
        if(contains(transactionHash)) {
            transactions.remove(transactionHash);
        }
    }

    /**
     * Remove all transactions from a specific user
     * @param player Player name
     * @return Count of deleted transactions
     */
    public synchronized int remove(String player) {
        int count = 0;
        for(Map.Entry<Integer, Transaction> entry : transactions.entrySet()) {
            if(entry.getValue().getSender().equalsIgnoreCase(player)) {
                remove(entry.getKey());
            }
        }
        return count;
    }

    /**
     * Check if a transaction is already in the Map
     */
    public boolean contains(Transaction transaction) {
        return transactions.containsValue(transaction);
    }

    /**
     * Check if a transaction is already in the Map
     */
    public boolean contains(int transactionHash) {
        return transactions.containsKey(transactionHash);
    }

    /**
     * Get a transaction from the Map using its hash code
     */
    public Transaction get(int transactionHash) {
        return transactions.get(transactionHash);
    }

    /**
     * Get a copy of the transactions in the Map
     */
    public Collection<Transaction> getTransactions() {
        return Collections.unmodifiableCollection(transactions.values());
    }

    /**
     * Create the transation file
     * @param replace Should the current file be overwritten
     */
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
