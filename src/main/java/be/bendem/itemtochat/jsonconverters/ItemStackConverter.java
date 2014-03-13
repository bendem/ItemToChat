package be.bendem.itemtochat.jsonconverters;

import be.bendem.itemtochat.ItemToChat;
import be.bendem.itemtochat.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

/**
 * @author bendem
 */
public class ItemStackConverter {

    private final ItemStack  itemStack;
    private final ItemToChat plugin;
    private final String     textBefore;
    private final String     textAfter;

    public ItemStackConverter(final ItemStack itemStack, final ItemToChat plugin) {
        this(itemStack, plugin, null, null);
    }

    public ItemStackConverter(final ItemStack itemStack, final ItemToChat plugin, String textBefore) {
        this(itemStack, plugin, textBefore, null);
    }

    public ItemStackConverter(final ItemStack itemStack, final ItemToChat plugin, final String textBefore, final String textAfter) {
        this.itemStack = itemStack;
        this.plugin = plugin;
        this.textBefore = textBefore;
        this.textAfter = textAfter;
    }

    public static String toTellRawCommand(String target, String json) {
        return "tellraw " + target + " " + json;
    }

    public String toJson() {
        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        json.addProperty("text", textBefore == null ? "" : textBefore);
        json.add("extra", createExtraSection());
        return gson.toJson(json);
    }

    private JsonArray createExtraSection() {
        JsonArray jsExtra = new JsonArray();
        JsonObject jsExtraSection = new JsonObject();

        // TODO Make text being client translated
        String itemText = Utils.capitalize(itemStack.getType().name().toLowerCase().replace("_", " "));
        if(itemStack.getAmount() > 1) {
            itemText += " x " + itemStack.getAmount();
        }
        jsExtraSection.addProperty("text", itemText);
        jsExtraSection.addProperty("color", getItemColor());
        jsExtraSection.add("hoverEvent", createHoverEventSection());

        JsonObject before = new JsonObject();
        JsonObject after = new JsonObject();

        before.addProperty("text", "[");
        after.addProperty("text", "]" + (this.textAfter == null ? "" : this.textAfter));

        jsExtra.add(before);
        jsExtra.add(jsExtraSection);
        jsExtra.add(after);

        return jsExtra;
    }

    private JsonObject createHoverEventSection() {
        JsonObject jsHoverEvent = new JsonObject();
        jsHoverEvent.addProperty("action", "show_item");
        jsHoverEvent.addProperty("value", createValueSection().toString());
        return jsHoverEvent;
    }

    @SuppressWarnings("deprecation")
    private JsonObject createValueSection() {
        JsonObject jsValue = new JsonObject();
        JsonObject jsTag = createTagSection();
        // TODO Use the new "minecraft:*" names
        jsValue.addProperty("id", itemStack.getType().getId());
        jsValue.addProperty("Damage", itemStack.getDurability());
        if(jsTag != null) {
            jsValue.add("tag", jsTag);
        }
        return jsValue;
    }

    private JsonObject createTagSection() {
        JsonObject jsTag = new JsonObject();
        JsonArray jsEnchants = createEnchantmentsSection();
        if(jsEnchants != null) {
            jsTag.add("ench", jsEnchants);
        }
        JsonObject jsFirework = createFireworkSection();
        if(jsEnchants != null) {
            jsTag.add("Fireworks", jsFirework);
        }
        JsonObject jsDisplay = createDisplaySection();
        if(jsDisplay != null) {
            jsTag.add("display", jsDisplay);
        }

        return jsTag;
    }

    private JsonObject createDisplaySection() {
        if(!itemStack.hasItemMeta()) {
            return null;
        }
        ItemMeta meta = itemStack.getItemMeta();
        if(!meta.hasDisplayName()) {
            return null;
        }
        JsonObject jsDisplay = new JsonObject();
        jsDisplay.addProperty("Name", meta.getDisplayName());
        return jsDisplay;
    }

    private JsonObject createFireworkSection() {
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

    private String getItemColor() {
        // TODO Create a config file for each in game item
        // TODO handle wool / stained glass / leather armor (using autodetect-colors)
        plugin.logger.info(itemStack.getType().name().toLowerCase());
        return plugin.getConfig().getString("item-colors." + itemStack.getType().name().toLowerCase(), "white");
    }

}
