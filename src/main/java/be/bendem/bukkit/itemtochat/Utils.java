package be.bendem.bukkit.itemtochat;

/**
 * @author bendem
 */
public class Utils {

    public static final int TICKS_BY_SECOND = 20;

    public static long secondsToTicks(long seconds) {
        return TICKS_BY_SECOND * seconds;
    }

}
