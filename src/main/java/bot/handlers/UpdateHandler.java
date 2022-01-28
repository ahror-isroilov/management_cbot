package bot.handlers;

import bot.repository.log.LogRepository;
import org.telegram.telegrambots.meta.api.objects.Update;


public class UpdateHandler {
    private final MessageHandler messageHandler = MessageHandler.getInstance();
    private final OtherHandlers otherHandlers = OtherHandlers.getInstance();
    private final CallbackHandler callbackHandler = CallbackHandler.getInstance();
    private final LogRepository log = new LogRepository();

    public void handle(Update update) {
        log.log(update);
        if (update.hasMessage()) {
            messageHandler.handle(update);
        } else if (update.hasCallbackQuery())
            callbackHandler.handle(update);
        else if (update.hasMyChatMember())
            otherHandlers.handle(update);
    }

    private static final UpdateHandler instance = new UpdateHandler();

    public static UpdateHandler getInstance() {
        return instance;
    }
}
