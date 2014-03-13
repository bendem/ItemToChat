package be.bendem.itemtochat;

/**
 * @author bendem
 */
public class Utils {

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
