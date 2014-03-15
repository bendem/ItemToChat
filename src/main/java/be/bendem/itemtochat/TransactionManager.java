package be.bendem.itemtochat;

import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;

/**
 * @author bendem
 */
public class TransactionManager {

    public static final String FILENAME = "transactions.yml";

    private final ItemToChat           plugin;
    private final HashSet<Transaction> transactions;
    private final File                 file;

    public TransactionManager(ItemToChat plugin) {
        this.plugin = plugin;
        this.transactions = new HashSet<>();

        file = new File(plugin.getDataFolder().getAbsolutePath() + File.separator + FILENAME);
        createFile(false);
    }

    public void loadTransactions() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        if(!config.contains("transactions")) {
            plugin.logger.info("Empty transaction file, ignored...");
            return;
        }

        ArrayList<LinkedHashMap> sections = (ArrayList) config.getList("transactions");
        for(LinkedHashMap section : sections) {
            add(Transaction.deserialize(section));
        }
    }

    public void saveTransactions() {
        YamlConfiguration config = new YamlConfiguration();
        ArrayList<MemorySection> serializedTransactions = new ArrayList<>();
        for(Transaction transaction : transactions) {
            plugin.logger.info("Serializing...");
            serializedTransactions.add(Transaction.serialize(transaction));
        }

        config.options().header("Do not modify this file except if you want your house to be destroyed by angry galactic camels!").copyHeader(true);
        config.set("transactions", serializedTransactions);
        try {
            config.save(file);
        } catch(IOException e) {
            plugin.logger.warning("Could not save transactions!");
        }
    }

    public void add(Transaction transaction) {
        transactions.add(transaction);
    }

    public void remove(Transaction transaction) {
        if(contains(transaction)) {
            transactions.remove(transaction);
        }
    }

    public boolean contains(Transaction transaction) {
        return transactions.contains(transaction);
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
