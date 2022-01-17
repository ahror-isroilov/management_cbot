package bot.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import static java.lang.Math.toIntExact;

public abstract class BaseAbstractHandler {

    protected SendMessage msgObject(long chatId, String text) {
        SendMessage sendMessage = new SendMessage(chatId + "", text);
        sendMessage.enableHtml(true);
        return sendMessage;
    }

    protected EditMessageText eMsgObject(Long chatId, Update update, String text) {
        long message_id = update.getCallbackQuery().getMessage().getMessageId();
        EditMessageText sendMessage = new EditMessageText();
        sendMessage.setText(text);
        sendMessage.setMessageId(toIntExact(message_id));
        sendMessage.setChatId(chatId.toString());
        sendMessage.enableHtml(true);
        return sendMessage;
    }
}
