package bot.repository.log;

import bot.configs.DbConfig;
import bot.repository.BaseRepository;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.sql.Types;

public class LogRepository extends BaseRepository {
    DbConfig config = DbConfig.getInstance();

    public void log(Update update) {
        String data = gson.toJson(update);
        prepareArguments(data);
        callProcedure(config.get("log.log"), Types.VARCHAR);
    }
}
