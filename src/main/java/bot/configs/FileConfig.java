package bot.configs;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class FileConfig {

    private static final FileConfig instance = new FileConfig();

    public static FileConfig getInstance() {
        return instance;
    }

    private static Properties p;

    static {
        try (FileReader reader = new FileReader("src/main/resources/db.properties")) {
            p = new Properties();
            p.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return p.getProperty(key);
    }
}
