package be.bendem.itemtochat.jsonconverters;

import be.bendem.itemtochat.ItemToChat;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author bendem
 */
public class FireworkStarConverter extends AbstractFireworkConverter {

    protected FireworkStarConverter(ItemToChat plugin, ItemStack itemStack) {
        super(plugin, itemStack);
    }

    @Override
    public JsonElement toJson() {
        return createExplosionSection();
    }

    private JsonObject createExplosionSection() {
        if(!itemStack.hasItemMeta()) {
            return null;
        }
        ItemMeta meta = itemStack.getItemMeta();
        if(!(meta instanceof FireworkEffectMeta)) {
            return null;
        }
        FireworkEffectMeta fireChargeMeta = (FireworkEffectMeta) itemStack.getItemMeta();
        return convertEffect(fireChargeMeta.getEffect());
    }

}
