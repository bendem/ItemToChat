package be.bendem.itemtochat;

import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.MemorySection;
import org.bukkit.inventory.ItemStack;

import java.util.LinkedHashMap;

/**
 * @author bendem
 */
public class Transaction {

    private final String    sender;
    private final ItemStack itemStack;
    private final long      timeStamp;
    private final int       lifeTime;

    public Transaction(String sender, ItemStack itemStack, long timeStamp, int lifeTime) {
        this.sender = sender;
        this.itemStack = itemStack;
        this.timeStamp = timeStamp;
        this.lifeTime = lifeTime;
    }

    public static Transaction deserialize(LinkedHashMap section) {
        if(!section.containsKey("sender") || !section.containsKey("itemstack") || !section.containsKey("timestamp") || !section.containsKey("lifetime")) {
            return null;
        }

        return new Transaction((String) section.get("sender"), (ItemStack) section.get("itemstack"), (long) section.get("timestamp"), (int) section.get("lifetime"));
    }

    public static MemorySection serialize(Transaction transaction) {
        MemoryConfiguration serialized = new MemoryConfiguration();

        serialized.set("sender", transaction.getSender());
        serialized.set("itemstack", transaction.getItemStack());
        serialized.set("timestamp", transaction.getTimeStamp());
        serialized.set("lifetime", transaction.getLifeTime());

        return serialized;
    }

    public String getSender() {
        return sender;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public int getLifeTime() {
        return lifeTime;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }
        if(o == null || getClass() != o.getClass()) {
            return false;
        }

        Transaction that = (Transaction) o;

        if(lifeTime != that.lifeTime) {
            return false;
        }
        if(timeStamp != that.timeStamp) {
            return false;
        }
        if(!itemStack.equals(that.itemStack)) {
            return false;
        }
        if(!sender.equals(that.sender)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = sender.hashCode();
        result = 31 * result + (itemStack != null ? itemStack.hashCode() : 0);
        result = 31 * result + (int) (timeStamp ^ (timeStamp >>> 32));
        result = 31 * result + lifeTime;
        return result;
    }
}
