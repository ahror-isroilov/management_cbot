package bot.configs;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class FileConfig {
    protected Properties p = new Properties();

    public FileConfig(String path) {
        load(path);
    }

    private void load(String path) {
        try (FileReader reader = new FileReader(path)) {
            p = new Properties();
            p.load(reader);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String get(String key) {
        return p.getProperty(key);
    }
}
