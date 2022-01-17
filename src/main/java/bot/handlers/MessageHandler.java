package bot.handlers;

import bot.Bot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
public class MessageHandler extends BaseAbstractHandler implements IBaseHandler {
    @Override
    public void process(Update update) {
        Bot bot = Bot.getInstance();
        Message message = update.getMessage();
        Long chatId = message.getChatId();
        String msgText = message.getText();
        User tgUser = message.getFrom();
        if (message.hasText()) {
            SendMessage sendMessage = msgObject(chatId, "Hello " + message.getFrom().getFirstName() +
                    "\n You write " + update.getMessage().getText());
            bot.executeMessage(sendMessage);
        }
    }

    private static final MessageHandler instance = new MessageHandler();

    public static MessageHandler getInstance() {
        return instance;
    }
}
