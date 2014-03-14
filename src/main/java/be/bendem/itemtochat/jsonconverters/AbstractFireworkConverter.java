package be.bendem.itemtochat.jsonconverters;

import be.bendem.itemtochat.ItemToChat;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * @author bendem
 */
public abstract class AbstractFireworkConverter extends AbstractJsonConverter {

    protected AbstractFireworkConverter(ItemToChat plugin, ItemStack itemStack) {
        super(plugin, itemStack);
    }

    public JsonObject convertEffect(FireworkEffect effect) {
        JsonObject jsEffect;
        JsonArray colors;
        jsEffect = new JsonObject();
        jsEffect.addProperty("Type", getEffectId(effect.getType()));
        jsEffect.addProperty("Flicker", effect.hasFlicker() ? 1 : 0);
        jsEffect.addProperty("Trail", effect.hasTrail() ? 1 : 0);

        colors = getColorsArray(effect.getColors());
        if(colors != null) {
            jsEffect.add("Colors", colors);
        }
        colors = getColorsArray(effect.getFadeColors());
        if(colors != null) {
            jsEffect.add("Colors", colors);
        }
        return jsEffect;
    }

    protected int getEffectId(FireworkEffect.Type type) {
        switch(type) {
            case BALL: return 0;
            case BALL_LARGE: return 1;
            case STAR: return 2;
            case CREEPER: return 3;
            case BURST: return 4;
        }
        return -1;
    }

    protected JsonArray getColorsArray(List<Color> colors) {
        JsonArray jsColors = new JsonArray();
        for(Color color : colors) {
            jsColors.add(new JsonPrimitive(color.asRGB()));
        }
        return jsColors.size() == 0 ? null : jsColors;
    }

}
