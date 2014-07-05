package be.bendem.bukkit.itemtochat.jsonconverters;

import be.bendem.bukkit.itemtochat.ItemToChat;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bendem
 */
public class BookConverter extends AbstractJsonConverter {

    private BookMeta bookMeta = null;

    public BookConverter(ItemToChat plugin, ItemStack itemStack) {
        super(plugin, itemStack);

        if(itemStack.hasItemMeta()) {
            ItemMeta meta = itemStack.getItemMeta();
            if(meta instanceof BookMeta) {
                bookMeta = (BookMeta) meta;
            }
        }
    }

    @Override
    public JsonElement toJson() {
        if(bookMeta == null) {
            return null;
        }
        List<String> pages = new ArrayList<>(bookMeta.getPages());
        for(int i = 0; i < pages.size(); i++) {
            // TODO Fix line breaks
            pages.set(i, pages.get(i).replace("\n", "\u0010"));
        }
        return listToJsonArray(pages);
    }

    public JsonPrimitive getAuthor() {
        if(bookMeta == null || bookMeta.getAuthor() == null) {
            return null;
        }
        return new JsonPrimitive(bookMeta.getAuthor());
    }

    public JsonElement getTitle() {
        if(bookMeta == null || bookMeta.getTitle() == null) {
            return null;
        }
        return new JsonPrimitive(bookMeta.getTitle());
    }

}
