package be.bendem.itemtochat;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.logging.Logger;

public class ItemStackToTellRaw {

    private final ItemStack  itemStack;
    private final ItemToChat plugin;

    ItemStackToTellRaw(ItemStack itemStack, ItemToChat plugin) {
        this.itemStack = itemStack;
        this.plugin = plugin;
    }

    public String toJson() {
        //! Uses custom gson.stream.JsonWriter
        //! line 402 was replaced by out.write(deferredName);
        //! to remove quotes surrounding names.
        Gson gson = new Gson();
        return gson.toJson(createExtraSection());
    }

    public String toTellRawCommand(String target) {
        return "/tellraw " + target + toJson();
    }

    private JsonArray createExtraSection() {
        JsonArray  jsExtra = new JsonArray();
        JsonObject jsExtraSection = new JsonObject();

        // TODO Make text being client translated
        jsExtraSection.addProperty("text", capitalize(itemStack.getType().name().toLowerCase().replace("_", " ")));
        jsExtraSection.addProperty("color", getItemColor());
        jsExtraSection.add("hoverEvent", createHoverEventSection());

        JsonObject before = new JsonObject();
        JsonObject after = new JsonObject();

        before.addProperty("text", "[");
        after.addProperty("text", "]");

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

    private JsonArray createEnchantmentsSection() {
        if(itemStack.getEnchantments().size() == 0) {
            return null;
        }
        JsonArray enchants = new JsonArray();

        for (Enchantment enchant : itemStack.getEnchantments().keySet()) {
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

    // TODO This has nothing to do here...
    public static String capitalize(final String str) {
        if (str.isEmpty()) {
            return str;
        }
        final char[] buffer = str.toCharArray();
        boolean capitalizeNext = true;
        for (int i = 0; i < buffer.length; i++) {
            final char ch = buffer[i];
            if (ch == ' ') {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                buffer[i] = Character.toTitleCase(ch);
                capitalizeNext = false;
            }
        }
        return new String(buffer);
    }

}
