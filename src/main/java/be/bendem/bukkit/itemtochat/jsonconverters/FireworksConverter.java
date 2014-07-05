package be.bendem.bukkit.itemtochat.jsonconverters;

import be.bendem.bukkit.itemtochat.ItemToChat;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.FireworkEffect;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author bendem
 */
class FireworksConverter extends AbstractFireworkConverter {

    protected FireworksConverter(ItemToChat plugin, ItemStack itemStack) {
        super(plugin, itemStack);
    }

    @Override
    public JsonElement toJson() {
        return createFireworksSection();
    }

    private JsonObject createFireworksSection() {
        if(!itemStack.hasItemMeta()) {
            return null;
        }
        ItemMeta meta = itemStack.getItemMeta();
        if(!(meta instanceof FireworkMeta)) {
            return null;
        }

        FireworkMeta fireworkMeta = (FireworkMeta) meta;
        JsonObject jsFirework = new JsonObject();
        JsonArray jsEffects = new JsonArray();
        for(FireworkEffect effect : fireworkMeta.getEffects()) {
            jsEffects.add(convertEffect(effect));
        }

        jsFirework.addProperty("Flight", fireworkMeta.getPower());
        jsFirework.add("Explosions", jsEffects);

        return jsFirework;
    }

}
