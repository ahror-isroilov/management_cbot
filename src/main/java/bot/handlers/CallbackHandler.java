package bot.handlers;

import bot.buttons.inline.InlineBoards;
import bot.buttons.markup.MarkupBoards;
import bot.models.Group;
import bot.security.SecurityHolder;
import bot.states.ActionState;
import bot.states.State;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;

import static bot.localization.Words.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CallbackHandler extends AbstractMethods implements IBaseHandler {
    State state = State.getInstance();
    EditMessageText editMessageText = new EditMessageText();
    List<Group> result = new ArrayList<>();

    @SneakyThrows
    @Override
    public void handle(Update update) {
        prepare(update);
        String data = callbackQuery.getData();
        result = SecurityHolder.getGroup(chatId);
        if ("join".equals(data)) {
            joinToBot();
        } else if ("accept".equals(data)) {
            if (SecurityHolder.getGroup(chatId).size() <= 0) {
                editMessageText = eMsgObject(chatId, update, "<b>Birorta ham guruh tanlanmadi</b>");
                bot.executeMessage(editMessageText);
                Thread.sleep(600);
                editMessageText = eMsgObject(chatId, update, "<b>Xabar yuboriladigan  guruhlarni tanlang</b>");
                editMessageText.setReplyMarkup(InlineBoards.prepareToAccept(groupList));
                bot.executeMessage(editMessageText);
                return;
            }
            state.setState(chatId, ActionState.SEND_MESSAGE.getCode());
            bot.executeMessage(new DeleteMessage(chatId.toString(), message.getMessageId()));
        } else if ("close".equals(data)) {
            bot.executeMessage(new DeleteMessage(chatId.toString(), message.getMessageId()));
        } else if (state.getState(chatId).equals(ActionState.DEFAULT.getCode())) {
            if (foundUnaccepted(data)) {
                activateGroup(update, data);
            } else if (foundAccepted(data)) {
                deactivateGroup(update, data);
            }
        } else if (state.getState(chatId).equals(ActionState.CHOOSE_GROUP.getCode())) {
            if (foundAccepted(data)) {
                Group gr = g_repository.getById(Long.parseLong(data));
                groupList = markButton(groupList, gr);
                SecurityHolder.setGroup(chatId, result);
                EditMessageText edm = eMsgObject(chatId, update, "<b>Xabar yuboriladigan guruhlarni tanlang</b>");
                edm.setReplyMarkup(InlineBoards.prepareToAccept(groupList));
                bot.executeMessage(edm);
            }
        }
    }

    private List<Group> markButton(List<Group> groupList, Group gr) {
        List<Group> groups = new ArrayList<>();
        for (Group group : groupList) {
            if (group.getGroupId().equals(gr.getGroupId())) {
                if (!group.getName().endsWith(" ✅")) {
                    group.setName(group.getName() + " ✅");
                    result.add(group);
                } else {
                    group.setName(group.getName().substring(0, group.getName().length() - 1));
                    result.remove(group);
                }
            }
            groups.add(group);
        }
        return groups;
    }

    private void deactivateGroup(Update update, String data) {
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

    private void activateGroup(Update update, String data) {
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
    }

    private void joinToBot() {
        bot.executeMessage(new DeleteMessage(chatId + "", message.getMessageId()));
        SendMessage sendMessage = msgObject(chatId, SEND_PHONE.get("uz"));
        sendMessage.setReplyMarkup(MarkupBoards.phoneButton());
        bot.executeMessage(sendMessage);
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

    public void reset_result() {
        result.clear();
    }
}
