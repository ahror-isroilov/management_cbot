package bot.handlers;

import org.telegram.telegrambots.meta.api.objects.Update;
import bot.services.LogService;
import bot.utils.Utils;


public class UpdateHandler {
    private static final UpdateHandler instance = new UpdateHandler();
    private final MessageHandler messageHandler = MessageHandler.getInstance();
    private final OtherHandlers otherHandlers = OtherHandlers.getInstance();
    private final CallbackHandler callbackHandler = CallbackHandler.getInstance();
    private final LogService logService = LogService.getInstance();

    public static UpdateHandler getInstance() {
        return instance;
    }

    public void handle(Update update) {
        //writeLog(update);
        if (update.hasMessage()) {
            messageHandler.process(update);
        } else if (update.hasCallbackQuery())
            callbackHandler.process(update);
        else if (update.hasMyChatMember())
            otherHandlers.process(update);
    }

    private void writeLog(Update update) {
        logService.save(Utils.withoutNulls().toJson(update));
    }
}
