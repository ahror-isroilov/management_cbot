package bot.processors;

import org.telegram.telegrambots.meta.api.objects.Update;

public interface IBaseProcessor {
    void process(Update update);
}
