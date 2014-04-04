package be.bendem.itemtochat.jsonconverters;

import be.bendem.itemtochat.ItemToChat;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import org.bukkit.inventory.ItemStack;

/**
 * @author bendem
 */
abstract class AbstractJsonConverter {

    protected final ItemToChat plugin;
    protected final ItemStack  itemStack;

    protected AbstractJsonConverter(ItemToChat plugin, ItemStack itemStack) {
        this.plugin = plugin;
        this.itemStack = itemStack;
    }

    abstract public JsonElement toJson();

    public String toString() {
        return new Gson().toJson(toJson());
    }

}
