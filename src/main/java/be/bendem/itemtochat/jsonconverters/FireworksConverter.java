package be.bendem.itemtochat.jsonconverters;

import be.bendem.itemtochat.ItemToChat;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.List;

/**
 * @author bendem
 */
class FireworksConverter extends AbstractJsonConverter {

    protected FireworksConverter(ItemToChat plugin, ItemStack itemStack) {
        super(plugin, itemStack);
    }

    @Override
    public JsonElement toJson() {
        return createFireworksSection();
    }

    private JsonObject createFireworksSection() {
        if(itemStack.getType() != Material.FIREWORK) {
            return null;
        }

        JsonObject jsFirework = new JsonObject();

        FireworkMeta fireworkMeta = (FireworkMeta) itemStack.getItemMeta();
        List<FireworkEffect> fireworkEffects = fireworkMeta.getEffects();

        jsFirework.addProperty("Flight", fireworkMeta.getPower());

        for(FireworkEffect effect : fireworkEffects) {
        }

        return jsFirework;
    }

}
