package bot.repository.log;

import bot.configs.FileConfig;
import bot.repository.AbstractRepository;

public class LogRepository extends AbstractRepository {
    private static final LogRepository instance = new LogRepository();

    public static LogRepository getInstance() {
        return instance;
    }

    public void save(String data) {
        query.append(FileConfig.get("query.log.insert"));
        getPreparedStatement(data);
        executeWithout();
    }
}
