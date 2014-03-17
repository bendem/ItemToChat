package be.bendem.itemtochat;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemoryConfiguration;
import org.bukkit.configuration.MemorySection;
import org.bukkit.inventory.ItemStack;

import java.util.Date;
import java.util.Map;

/**
 * @author bendem
 */
public class Transaction {

    private final String    sender;
    private final ItemStack itemStack;
    private final long      timeStamp;
    private final long      lifeTime;
    private final Type type;

    public Transaction(String sender, ItemStack itemStack, long timeStamp, long lifeTime) {
        this(sender, itemStack, timeStamp, lifeTime, Type.Send);
    }

    public Transaction(String sender, ItemStack itemStack, long timeStamp, long lifeTime, Type type) {
        this.sender = sender;
        this.itemStack = itemStack;
        this.timeStamp = timeStamp;
        this.lifeTime = lifeTime;
        this.type = type;
    }

    public static Transaction deserialize(ConfigurationSection section) {
        if(!section.contains("sender") || !section.contains("itemstack") || !section.contains("timestamp") || !section.contains("lifetime")) {
            return null;
        }

        String sender = section.getString("sender");
        ItemStack itemStack = section.getItemStack("itemstack");
        long timestamp = section.getLong("timestamp");
        long lifetime = section.getLong("lifetime");

        return new Transaction(sender, itemStack, timestamp, lifetime);
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

    public long getLifeTime() {
        return lifeTime;
    }

    public Type getType() {
        return type;
    }

    public boolean isValid() {
        return new Date().getTime() < timeStamp + lifeTime;
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
    public String toString() {
        return "Transaction{" +
                "sender='" + sender + "'" +
                ", itemStack=" + itemStack +
                ", timeStamp=" + timeStamp +
                ", lifeTime=" + lifeTime +
                ", type=" + type +
                '}';
    }

    @Override
    public int hashCode() {
        int result = sender.hashCode();
        result = 31 * result + itemStack.hashCode();
        result = 31 * result + (int) (timeStamp ^ (timeStamp >>> 32));
        result = 31 * result + (int) (lifeTime ^ (lifeTime >>> 32));
        result = 31 * result + type.hashCode();
        return result;
    }

    public enum Type {
        Send,
        Give
    }

}
