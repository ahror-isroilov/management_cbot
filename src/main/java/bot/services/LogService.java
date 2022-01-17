package bot.services;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import bot.mappers.BaseMapper;
import bot.repository.log.LogRepository;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LogService extends AbstractService<LogRepository, BaseMapper> {

    public void save(String data) {
        repository.save(data);
    }

    private static final LogService instance = new LogService(LogRepository.getInstance());

    public LogService(LogRepository repository) {
        super(repository,null);
    }

    public static LogService getInstance() {
        return instance;
    }
}
