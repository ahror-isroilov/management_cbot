package bot.configs;

public class DbConfig extends FileConfig {

    private static DbConfig instance = new DbConfig();

    public static DbConfig getInstance() {
        return instance;
    }

    public DbConfig() {
        super("src/main/resources/db.properties");
    }


}
