package bot.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author Elmurodov Javohir, Wed 9:55 AM. 12/15/2021
 */
public class Utils {
    public static String genUniqueToken() {
        return System.nanoTime() + RandomStringUtils.random(16, true, true);
    }

    public static Gson withoutNulls() {
        return new GsonBuilder().setPrettyPrinting().create();
    }

    public static Gson withNulls() {
        return new GsonBuilder().serializeNulls().setPrettyPrinting().create();
    }
}
