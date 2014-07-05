package be.bendem.bukkit.itemtochat.jsonconverters;

import be.bendem.bukkit.itemtochat.ItemToChat;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

/**
 * @author bendem
 */
class EnchantmentsConverter extends AbstractJsonConverter {

    protected EnchantmentsConverter(ItemToChat plugin, ItemStack itemStack) {
        super(plugin, itemStack);
    }

    @Override
    public JsonElement toJson() {
        return createEnchantmentsSection();
    }

    @SuppressWarnings("deprecation")
    private JsonArray createEnchantmentsSection() {
        if(itemStack.getEnchantments().size() == 0) {
            return null;
        }
        JsonArray enchants = new JsonArray();

        for(Enchantment enchant : itemStack.getEnchantments().keySet()) {
            JsonObject jsEnchant = new JsonObject();
            // TODO Use the new minecraft names
            // getId is deprecated but we need it since minecraft don't handle bukkit values
            jsEnchant.addProperty("id", enchant.getId());
            jsEnchant.addProperty("lvl", itemStack.getEnchantmentLevel(enchant));
            enchants.add(jsEnchant);
        }

        return enchants;
    }

}
