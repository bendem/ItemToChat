package be.bendem.bukkit.itemtochat;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

/**
 * @author bendem
 */
public class TransactionManager {

    public static final String FILENAME = "transactions.yml";

    private final ItemToChat                    plugin;
    private final HashMap<Integer, Transaction> transactions;
    private final File                          file;
    private volatile boolean dirty = false;

    public TransactionManager(ItemToChat plugin) {
        this.plugin = plugin;
        this.transactions = new HashMap<>();

        file = new File(plugin.getDataFolder(), FILENAME);
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
            Transaction transaction = Transaction.deserialize(config.getConfigurationSection(key));
            if(transaction.isValid()) {
                add(transaction);
            }
        }
        clean();
    }

    /**
     * Save all the transactions to the file (the transactions in the file are lost)
     */
    public synchronized void saveTransactions() {
        if(!dirty) {
            return;
        }
        plugin.logger.info("Saving transactions...");
        YamlConfiguration config = new YamlConfiguration();
        config.options().header("Do not modify this file unless you want your house to be destroyed by angry galactic camels!").copyHeader(true);

        for(Transaction transaction : transactions.values()) {
            // TODO  Check why hashCode don't always return the same value :/
            if(transaction.isValid()) {
                config.set("transactions." + transaction.hashCode(), Transaction.serialize(transaction));
            }
        }

        try {
            config.save(file);
            dirty = false;
        } catch(IOException e) {
            plugin.logger.warning("Could not save transactions!");
        }
    }

    /**
     * Remove all invalid transactions from the Map
     */
    public synchronized void clean() {
        Iterator<Map.Entry<Integer, Transaction>> it = transactions.entrySet().iterator();
        while(it.hasNext()) {
            if(!it.next().getValue().isValid()) {
                it.remove();
                dirty = true;
            }
        }
    }

    /**
     * Add a transaction to the Map
     *
     * @return The hash of the map or 0 if it hasn't been inserted
     */
    public synchronized int add(Transaction transaction) {
        if(transaction.isValid() && !contains(transaction)) {
            transactions.put(transaction.hashCode(), transaction);
            dirty = true;
            return transaction.hashCode();
        }
        return 0;
    }

    /**
     * Remove a specific transaction
     *
     * @param transaction Transaction to remove
     */
    public synchronized void remove(Transaction transaction) {
        remove(transaction.hashCode());
        dirty = true;
    }

    /**
     * Remove a specific transaction
     *
     * @param transactionHash Hash code of the transaction
     */
    public synchronized void remove(int transactionHash) {
        if(contains(transactionHash)) {
            transactions.remove(transactionHash);
            dirty = true;
        }
    }

    /**
     * Remove all transactions from a specific user
     *
     * @param player Player name
     *
     * @return Count of deleted transactions
     */
    public synchronized int remove(UUID player) {
        int count = 0;
        Iterator<Map.Entry<Integer, Transaction>> it = transactions.entrySet().iterator();
        while(it.hasNext()) {
            if(it.next().getValue().getSender().equals(player)) {
                it.remove();
                ++count;
            }
        }
        if(count > 0) {
            dirty = true;
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
     *
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
