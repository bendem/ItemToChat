package be.bendem.itemtochat.jsonconverters;

import be.bendem.itemtochat.ItemToChat;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.bukkit.inventory.ItemStack;

import java.util.List;

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

    public static JsonArray listToJsonArray(List<String> list) {
        JsonArray jsArray = new JsonArray();
        for(String string : list) {
            if(string != null) {
                jsArray.add(new JsonPrimitive(string));
            }
        }
        return jsArray;
    }

}
