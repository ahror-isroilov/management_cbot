package bot.handlers;

import bot.models.Group;
import bot.repository.AuthRepository;
import bot.repository.GroupRepository;
import bot.repository.log.LogRepository;
import org.telegram.telegrambots.meta.api.objects.Update;


public class UpdateHandler {
    private final MessageHandler messageHandler = MessageHandler.getInstance();
    private final OtherHandlers otherHandlers = OtherHandlers.getInstance();
    private final CallbackHandler callbackHandler = CallbackHandler.getInstance();
    private final LogRepository log = new LogRepository();
    GroupRepository gr = GroupRepository.getInstance();
    AuthRepository ar = AuthRepository.getInstance();

    public void handle(Update update) {
        CRON(update);
        log.log(update);
        if (update.hasMessage()) {
            messageHandler.handle(update);
        } else if (update.hasCallbackQuery()) callbackHandler.handle(update);
        else if (update.hasMyChatMember()) {
            otherHandlers.handle(update);
        }
    }

    public void CRON(Update update) {
        if (update.hasMessage()) {
            gr.updateName(update.getMessage().getChat().getTitle(), update.getMessage().getChat().getId());
            ar.update_worker(update.getMessage().getFrom().getFirstName(), update.getMessage().getFrom().getLastName(), update.getMessage().getFrom().getId());
        }
    }

    private static final UpdateHandler instance = new UpdateHandler();

    public static UpdateHandler getInstance() {
        return instance;
    }
}
