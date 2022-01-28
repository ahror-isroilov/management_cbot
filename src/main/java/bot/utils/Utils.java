package bot.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * @author Elmurodov Javohir, Wed 9:55 AM. 12/15/2021
 */
@Getter
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

    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static final Utils instance = new Utils();

    public static Utils getInstance() {
        return instance;
    }
}
