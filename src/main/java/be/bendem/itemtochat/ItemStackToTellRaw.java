package be.bendem.itemtochat;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackToTellRaw {

    private final ItemStack  itemStack;
    private final ItemToChat plugin;
    private final String before;
    private final String after;

    ItemStackToTellRaw(final ItemStack itemStack, final ItemToChat plugin) {
        this(itemStack, plugin, null, null);
    }

    ItemStackToTellRaw(final ItemStack itemStack, final ItemToChat plugin, String before) {
        this(itemStack, plugin, before, null);
    }

    ItemStackToTellRaw(final ItemStack itemStack, final ItemToChat plugin, final String before, final String after) {
        this.itemStack = itemStack;
        this.plugin = plugin;
        this.before = before;
        this.after = after;
    }

    public String toJson() {
        Gson gson = new Gson();
        JsonObject json = new JsonObject();
        json.addProperty("text", before == null ? "" : before);
        json.add("extra", createExtraSection());
        return gson.toJson(json);
    }

    private JsonArray createExtraSection() {
        JsonArray  jsExtra = new JsonArray();
        JsonObject jsExtraSection = new JsonObject();

        // TODO Make text being client translated
        String itemText =  capitalize(itemStack.getType().name().toLowerCase().replace("_", " "));
        if(itemStack.getAmount() > 1) {
            itemText += " x " + itemStack.getAmount();
        }
        jsExtraSection.addProperty("text", itemText);
        jsExtraSection.addProperty("color", getItemColor());
        jsExtraSection.add("hoverEvent", createHoverEventSection());

        JsonObject before = new JsonObject();
        JsonObject after = new JsonObject();

        before.addProperty("text", "[");
        after.addProperty("text", "]" + (this.after == null ? "" : this.after));

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

    @SuppressWarnings("deprecation")
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

    public static String toTellRawCommand(String target, String json) {
        return "tellraw " + target + " " + json;
    }

}
