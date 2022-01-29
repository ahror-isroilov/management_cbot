package bot.handlers;

import bot.buttons.inline.InlineBoards;
import bot.buttons.markup.MarkupBoards;
import bot.models.Group;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import static bot.localization.Words.*;

import java.util.List;
import java.util.Objects;

public class CallbackHandler extends AbstractMethods implements IBaseHandler {

    @Override
    public void handle(Update update) {
        prepare(update);
        String data = callbackQuery.getData();
        if ("join".equals(data)) {
            bot.executeMessage(new DeleteMessage(chatId + "", message.getMessageId()));
            SendMessage sendMessage = msgObject(chatId, SEND_PHONE.get("uz"));
            sendMessage.setReplyMarkup(MarkupBoards.phoneButton());
            bot.executeMessage(sendMessage);
        } else if ("close".equals(data)) {
            bot.executeMessage(new DeleteMessage(chatId.toString(), message.getMessageId()));
        } else if (foundUnaccepted(data)) {
            g_repository.acceptGroup(Long.parseLong(data));
            List<Group> groupList = g_repository.getUnAcceptedList();
            if (groupList.size() > 0) {
                EditMessageText editMessageText = eMsgObject(chatId, update, PRESS_FOR_ACTIVATE.get("uz"));
                editMessageText.setReplyMarkup(InlineBoards.prepareButtons(groupList));
                bot.executeMessage(editMessageText);
            } else {
                EditMessageText editMessageText = eMsgObject(chatId, update, ALL_GROUPS_ACTIVATED1.get("uz"));
                bot.executeMessage(editMessageText);
            }
        } else if (foundAccepted(data)) {
            g_repository.unAcceptGroup(Long.parseLong(data));
            List<Group> acceptedList = g_repository.getAcceptedList();
            EditMessageText editMessageText = eMsgObject(chatId, update, "");
            if (acceptedList.size() < 1) {
                editMessageText.setText(ALL_GROUPS_DELETED.get("uz"));
                bot.executeMessage(editMessageText);
                return;
            }
            editMessageText.setText(PRESS_FOR_DELETE.get("uz"));
            editMessageText.setReplyMarkup(InlineBoards.prepareButtons(acceptedList));
            bot.executeMessage(editMessageText);
        }
    }

    private boolean foundUnaccepted(String Id) {
        Group group = g_repository.getById(Long.parseLong(Id));
        return Objects.nonNull(group) && !group.getAccepted();
    }

    private boolean foundAccepted(String Id) {
        Group group = g_repository.getById(Long.parseLong(Id));
        return Objects.nonNull(group) && group.getAccepted();
    }

    private static final CallbackHandler instance = new CallbackHandler();

    public static CallbackHandler getInstance() {
        return instance;
    }
}
