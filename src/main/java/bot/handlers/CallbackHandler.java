package bot.handlers;

import org.telegram.telegrambots.meta.api.objects.Update;

public class CallbackHandler extends BaseAbstractHandler implements IBaseHandler {
    @Override
    public void process(Update update) {

    }

    private static final CallbackHandler instance = new CallbackHandler();

    public static CallbackHandler getInstance() {
        return instance;
    }
}
