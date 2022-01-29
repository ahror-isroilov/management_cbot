package bot.handlers;

import bot.Bot;
import bot.models.Group;
import bot.repository.AuthRepository;
import bot.repository.GroupRepository;
import bot.security.SecurityHolder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.lang.Math.toIntExact;

public abstract class AbstractMethods {
    AuthRepository repository = AuthRepository.getInstance();
    GroupRepository g_repository = GroupRepository.getInstance();
    protected Message message;
    protected CallbackQuery callbackQuery;
    protected String mText;
    protected User user;
    protected Long chatId;
    protected Bot bot;
    protected String firstname;
    protected String phoneNumber;
    protected static List<Group> groupList = new ArrayList<>();

    protected void prepare(Update update) {
        if (update.hasCallbackQuery()) message = update.getCallbackQuery().getMessage();
        else message = update.getMessage();
        callbackQuery = update.getCallbackQuery();
        bot = Bot.getInstance();
        mText = message.getText();
        chatId = message.getChatId();
        user = message.getFrom();
    }

    protected void getNameAndPhone(String phone, String v_firstname) {
        phoneNumber = phone;
        firstname = v_firstname;
    }

    protected SendMessage msgObject(long chatId, String text) {
        SendMessage sendMessage = new SendMessage(chatId + "", text);
        sendMessage.enableHtml(true);
        return sendMessage;
    }

    protected EditMessageText eMsgObject(Long chatId, Update update, String text) {
        long message_id;
        if (update.hasCallbackQuery()) message_id = update.getCallbackQuery().getMessage().getMessageId();
        else message_id = update.getMessage().getMessageId();
        EditMessageText sendMessage = new EditMessageText();
        sendMessage.setText(text);
        sendMessage.setMessageId(toIntExact(message_id));
        sendMessage.setChatId(chatId.toString());
        sendMessage.enableHtml(true);
        return sendMessage;
    }

    protected SendPhoto ePhoto(Long chatId, String caption, String path) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setPhoto(new InputFile(new File(path)));
        sendPhoto.setChatId(chatId.toString());
        sendPhoto.setCaption(caption);
        sendPhoto.setParseMode("HTML");
        return sendPhoto;
    }

    protected Boolean hasLoggedIn(Long user_id) {
        bot.models.User user = repository.getByUserId(user_id);
        return Objects.nonNull(user) && user.getLoggedIn();
    }

    protected Boolean isActive(Long user_id) {
        bot.models.User user = repository.getByUserId(user_id);
        return Objects.nonNull(user) && user.getLoggedIn() && !user.getBlocked();
    }

    protected Boolean isAdmin(Long user_id) {
        bot.models.User user = repository.getByUserId(user_id);
        return Objects.nonNull(user) && user.getRole().equals("ADMIN");
    }

    protected Boolean isWorker(Long user_id) {
        bot.models.User user = repository.getByUserId(user_id);
        return user.getRole().equals("WORKER");
    }

    protected void fillList() {
        groupList = g_repository.getAcceptedList();
    }

    protected void resetList() {
        groupList.clear();
    }
}
