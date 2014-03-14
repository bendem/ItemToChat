package be.bendem.itemtochat.jsonconverters;

import be.bendem.itemtochat.ItemToChat;
import be.bendem.itemtochat.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * @author bendem
 */
public class ItemStackConverter extends AbstractJsonConverter {

    private final String textBefore;
    private final String textAfter;

    public ItemStackConverter(final ItemToChat plugin, final ItemStack itemStack) {
        super(plugin, itemStack);
        this.textBefore = null;
        this.textAfter = null;
    }

    public ItemStackConverter(final ItemToChat plugin, final ItemStack itemStack, final String textBefore) {
        this(plugin, itemStack, textBefore, null);
    }

    public ItemStackConverter(final ItemToChat plugin, final ItemStack itemStack, final String textBefore, final String textAfter) {
        super(plugin, itemStack);
        this.textBefore = textBefore;
        this.textAfter = textAfter;
    }

    public static String toTellRawCommand(String target, String json) {
        Gson gson = new Gson();
        return "tellraw " + target + " " + json;
    }

    public String toTellRawCommand(String target) {
        Gson gson = new Gson();
        return "tellraw " + target + " " + gson.toJson(toJson());
    }

    public JsonElement toJson() {
        JsonObject json = new JsonObject();
        json.addProperty("text", textBefore == null ? "" : textBefore);
        json.add("extra", createExtraSection());
        return json;
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
        // Add Enchants info
        JsonElement jsEnchants = new EnchantmentsConverter(plugin, itemStack).toJson();
        if(jsEnchants != null) {
            jsTag.add("ench", jsEnchants);
        }
        // Add Fireworks info
        JsonElement jsFireworks = new FireworksConverter(plugin, itemStack).toJson();
        if(jsFireworks != null) {
            jsTag.add("Fireworks", jsFireworks);
        }
        // Add Display info
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

    private String getItemColor() {
        // TODO Create a config file for each in game item
        // TODO handle wool / stained glass / leather armor (using autodetect-colors)
        plugin.logger.info(itemStack.getType().name().toLowerCase());
        return plugin.getConfig().getString("item-colors." + itemStack.getType().name().toLowerCase(), "white");
    }

}
