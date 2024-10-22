package bot.handlers;

import bot.buttons.inline.InlineBoards;
import bot.buttons.markup.MarkupBoards;
import bot.localization.Words;
import bot.models.Group;
import bot.models.Requests;
import bot.models.User;
import bot.processors.AuthProcessor;
import bot.states.ActionState;
import bot.states.State;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import static bot.localization.Words.*;

import java.util.List;

public class MessageHandler extends AbstractMethods implements IBaseHandler {

    AuthProcessor authProcess = AuthProcessor.getInstance();
    SendMessage sendMessage = new SendMessage();
    State state = State.getInstance();

    @Override
    public void handle(Update update) {
        prepare(update);
        //state.setState(chatId, ActionState.DEFAULT.getCode());
        List<Group> unAcceptedList = g_repository.getUnAcceptedList();
        List<Group> acceptedList = g_repository.getAcceptedList();
        if (message.hasText() && message.hasText() && mText.equals("/start")) {
            state.setState(chatId, ActionState.DEFAULT.getCode());
            if (hasLoggedIn(user.getId())) {
                sendHello();
            } else {
                sendAuthorizeMessage();
            }
        } else if (!hasLoggedIn(user.getId()) && message.hasContact()) {
            authProcess.process(update);
        } else if (message.hasText() && message.hasText() && mText.equals("/about")) {
            SendMessage sendMessage = msgObject(chatId, Words.ABOUT.get("uz"));
            bot.executeMessage(sendMessage);
            SendLocation location = new SendLocation();
            location.setChatId(chatId.toString());
            location.setLatitude(40.3455741);
            location.setLongitude(70.7930828);
            bot.sendLocation(location);
        } else if (hasLoggedIn(chatId) && !isWorker(chatId) && !isAdmin(chatId) && message.hasText() && mText.equals("/request")) {
            User user = repository.getByUserId(message.getFrom().getId());
            repository.sendRequest(user.getFirstName(), user.getPhoneNumber(), user.getUserId());
            bot.executeMessage(sendMessage = msgObject(chatId, Words.REQUEST_SENT.get("uz")));
        } else if (hasLoggedIn(chatId) && isAdmin(chatId) && message.hasText() && mText.equals("Faollik \uD83D\uDD05")) {
            sendMessage = msgObject(chatId, "<b>Faollik</b>");
            sendMessage.setReplyMarkup(MarkupBoards.activity());
            bot.executeMessage(sendMessage);
        } else if (hasLoggedIn(chatId) && isAdmin(chatId) && message.hasText() && mText.equals("So'rovlar 📬")) {
            List<Requests> reqs = repository.getReqs();
            if (reqs.size() < 1) {
                sendMessage = msgObject(chatId, REQUEST_EMPTY.get("uz"));
                bot.executeMessage(sendMessage);
                return;
            }
            executeRequests(reqs);
        } else if (hasLoggedIn(chatId) && isAdmin(chatId) && message.hasText() && mText.equals("Ishchilar 👷‍♂️")) {
            List<User> userList = repository.userList();
            if (userList.size() < 1) {
                sendMessage = msgObject(chatId, ACTIVE_USERS_EMPTY.get("uz"));
                bot.executeMessage(sendMessage);
                return;
            }
            printUsers(userList);
        } else if (hasLoggedIn(chatId) && isAdmin(chatId) && message.hasText() && mText.equals("Faollik \uD83D\uDD05")) {
            sendMessage = msgObject(chatId, "<b>Faollik</b>");
            sendMessage.setReplyMarkup(MarkupBoards.activity());
            bot.executeMessage(sendMessage);
        } else if (hasLoggedIn(chatId) && isAdmin(chatId) && message.hasText() && mText.equals("Guruhni faollashtirish 🔰")) {
            if (unAcceptedList.size() > 0) {
                sendMessage = msgObject(chatId, PRESS_FOR_ACTIVATE.get("uz"));
                sendMessage.setReplyMarkup(InlineBoards.prepareButtons(unAcceptedList));
            } else sendMessage = msgObject(chatId, ALL_GROUPS_ACTIVATED.get("uz"));
            bot.executeMessage(sendMessage);
        } else if (hasLoggedIn(chatId) && isAdmin(chatId) && message.hasText() && mText.equals("Guruhni o'chirish ➖")) {
            if (acceptedList.size() > 0) sendMessage = msgObject(chatId, PRESS_FOR_DELETE.get("uz"));
            else sendMessage = msgObject(chatId, EMPTY_ACTIVE_GROUPS.get("uz"));
            sendMessage.setReplyMarkup(InlineBoards.prepareButtons(acceptedList));
            bot.executeMessage(sendMessage);
        } else if (hasLoggedIn(chatId) && isAdmin(chatId) && message.hasText() && mText.equals("Guruhlar 👥")) {
            if (acceptedList.size() > 0) sendMessage = msgObject(chatId, prepareText(acceptedList));
            else sendMessage = msgObject(chatId, EMPTY_ACTIVE_GROUPS.get("uz"));
            bot.executeMessage(sendMessage);
        } else if (hasLoggedIn(chatId) && message.hasText() && mText.equals("Orqaga 🔙")) {
            if (isAdmin(chatId)) {
                sendMessage = msgObject(chatId, "Bosh menyu 🏘");
                sendMessage.setReplyMarkup(MarkupBoards.adminMenu());
                state.setState(chatId, ActionState.DEFAULT.getCode());
            } else if (isWorker(chatId)) {
                sendMessage = msgObject(chatId, "Bosh menyu 🏘");
                sendMessage.setReplyMarkup(MarkupBoards.workerMenu());
                state.setState(chatId, ActionState.DEFAULT.getCode());
            }
            bot.executeMessage(sendMessage);
        } else if (hasLoggedIn(chatId) && isAdmin(chatId) && message.hasText() && mText.equals("Ishchi qo'shish ➕")) {
            state.setState(chatId, ActionState.ADD_WORKER.getCode());
            sendMessage = msgObject(chatId, ENTER_PHONE_FADD.get("uz"));
            bot.executeMessage(sendMessage);
        } else if (hasLoggedIn(chatId) && isAdmin(chatId) && state.getState(chatId).equals(ActionState.ADD_WORKER.getCode())) {
            String phone = message.getText();
            if (validPhone(phone)) {
                if (!repository.isReal(phone)) {
                    sendMessage = msgObject(chatId, USER_NOT_FOUND.get("uz"));
                    bot.executeMessage(sendMessage);
                    return;
                }
                repository.add_worker(phone);
                sendMessage = msgObject(chatId, SUCCESSFULLY_ADDED.get("uz"));
                bot.executeMessage(sendMessage);
                User user1 = repository.getByPhone(phone);
                sendMessage = msgObject(user1.getChatId(), JOINED.get("uz"));
                state.setState(chatId, ActionState.DEFAULT.getCode());
                bot.executeMessage(sendMessage);
            } else {
                sendMessage = msgObject(chatId, WRONG_PHONE.get("uz"));
                bot.executeMessage(sendMessage);
            }
        } else if (hasLoggedIn(chatId) && isAdmin(chatId) && message.hasText() && mText.equals("Ishchini bloklash ⛔️")) {
            state.setState(chatId, ActionState.DELETE_WORKER.getCode());
            sendMessage = msgObject(chatId, ENTER_PHONE_FREMOVE.get("uz"));
            bot.executeMessage(sendMessage);
        } else if (hasLoggedIn(chatId) && isAdmin(chatId) && state.getState(chatId).equals(ActionState.DELETE_WORKER.getCode())) {
            String phone = message.getText();
            if (validPhone(phone)) {
                if (!repository.isReal(phone)) {
                    sendMessage = msgObject(chatId, USER_NOT_FOUND.get("uz"));
                    bot.executeMessage(sendMessage);
                    return;
                }
                repository.block_worker(phone);
                sendMessage = msgObject(chatId, BLOCKED.get("uz"));
                bot.executeMessage(sendMessage);
                User user1 = repository.getByPhone(phone);
                sendMessage = msgObject(user1.getChatId(), BLOCKED_BY.get("uz"));
                state.setState(chatId, ActionState.DEFAULT.getCode());
                bot.executeMessage(sendMessage);
            } else {
                sendMessage = msgObject(chatId, WRONG_PHONE.get("uz"));
                bot.executeMessage(sendMessage);
            }
        } else if (hasLoggedIn(chatId) && isWorker(chatId) && message.hasText() && mText.equals("Yuborish 📨")) {
            if (isActive(chatId)) {
                state.setState(chatId, ActionState.SEND_MESSAGE.getCode());
                sendMessage = msgObject(chatId, SEND_FOR_SEND.get("uz"));
                sendMessage.setReplyMarkup(MarkupBoards.back());
                bot.executeMessage(sendMessage);
            } else {
                sendMessage = msgObject(chatId, YOU_ARE_BLOCKED.get("uz"));
                sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
                bot.executeMessage(sendMessage);
            }
        } else if (hasLoggedIn(chatId) && isWorker(chatId) && state.getState(chatId).equals(ActionState.SEND_MESSAGE.getCode())) {
            prepareDocument(update);
            state.setState(chatId, ActionState.SEND_MESSAGE.getCode());
        } else {
            if (chatId > 0) {
                sendMessage = msgObject(chatId, WRONG_COMMAND.get("uz"));
                bot.executeMessage(sendMessage);
            }
        }
    }

    private void prepareDocument(Update update) {
        List<Group> groupList = g_repository.getAcceptedList();
        if (update.getMessage().hasDocument()) {
            Document doc = message.getDocument();
            SendDocument document = new SendDocument();
            String fromName = message.getFrom().getFirstName();
            Long fromid = message.getFrom().getId();
            document.setDocument(new InputFile(doc.getFileId()));
            document.setParseMode("HTML");
            document.setCaption("<b>Yuboruvchi: <a href=\"tg://user?id=%s\">%s</a> </b>".formatted(fromid, fromName));
            for (Group group : groupList) {
                document.setChatId(group.getGroupId().toString());
                bot.send(document);
            }
            sendMessage = msgObject(chatId, SUCCESSFULLY_SENT.get("uz"));
            sendMessage.setReplyMarkup(MarkupBoards.back());
            bot.executeMessage(sendMessage);
            return;
        } else if (update.getMessage().hasPhoto()) {
            List<PhotoSize> messagePhoto = message.getPhoto();
            SendPhoto photo = new SendPhoto();
            String fromName = message.getFrom().getFirstName();
            Long fromid = message.getFrom().getId();
            photo.setPhoto(new InputFile(messagePhoto.get(3).getFileId()));
            photo.setParseMode("HTML");
            photo.setCaption("<b>Yuboruvchi: <a href=\"tg://user?id=%s\">%s</a> </b>".formatted(fromid, fromName));
            for (Group group : groupList) {
                photo.setChatId(group.getGroupId().toString());
                bot.sendPhoto(photo);
            }
            sendMessage = msgObject(chatId, SUCCESSFULLY_SENT.get("uz"));
            sendMessage.setReplyMarkup(MarkupBoards.back());
            bot.executeMessage(sendMessage);
            return;
        } else if (update.getMessage().hasText()) {
            String text = message.getText();
            String fromName = message.getFrom().getFirstName();
            Long fromid = message.getFrom().getId();
            for (Group group : groupList) {
                sendMessage = msgObject(group.getGroupId(), "<b>Yuboruvchi: <a href=\"tg://user?id=%s\">%s\n</a>%s </b>".formatted(fromid, fromName, text));
                bot.executeMessage(sendMessage);
            }
            sendMessage = msgObject(chatId, SUCCESSFULLY_SENT.get("uz"));
            sendMessage.setReplyMarkup(MarkupBoards.back());
            bot.executeMessage(sendMessage);
            return;
        }
        sendMessage = msgObject(chatId, WRONG_SENT.get("uz"));
        sendMessage.setReplyMarkup(MarkupBoards.back());
        bot.executeMessage(sendMessage);
    }

    private void printUsers(List<User> userList) {
        StringBuilder sb = new StringBuilder();
        sb.append(ACTIVE_USERS_LIST.get("uz"));
        int count = 1;
        for (User user1 : userList) {
            sb.append(USER_DETAILS.get("uz").formatted(count, user1.getUserId(), user1.getFirstName(), user1.getPhoneNumber()));
            count++;
        }
        sendMessage = msgObject(chatId, sb.toString());
        bot.executeMessage(sendMessage);
    }

    private void executeRequests(List<Requests> reqs) {
        for (Requests req : reqs) {
            sendMessage = msgObject(chatId, REQ_DETAILS.get("uz").formatted(req.getUserId(), req.getUsername(), req.getPhoneNumber(), req.getCreatedAt().substring(0, 19)));
            bot.executeMessage(sendMessage);
        }
        for (Requests req : reqs) {
            repository.seeReqs(req.getId());
        }
    }

    private boolean validPhone(String phone) {
        return phone.matches("^(998)[0-9]{9}$");
    }

    private String prepareText(List<Group> acceptedList) {
        StringBuilder builder = new StringBuilder();
        int a = 1;
        for (Group group : acceptedList) {
            builder.append("<b>").append(a).append(" - ").append(group.getName()).append("</b>\n");
            a++;
        }
        return builder.toString();
    }

    private void sendHello() {
        if (isAdmin(user.getId())) {
            sendMessage = msgObject(chatId, HI.get("uz"));
            sendMessage.setReplyMarkup(MarkupBoards.adminMenu());
            bot.executeMessage(sendMessage);
        } else if (isWorker(user.getId())) {
            sendMessage = msgObject(chatId, HI.get("uz"));
            sendMessage.setReplyMarkup(MarkupBoards.workerMenu());
            bot.executeMessage(sendMessage);
        } else {
            sendMessage = msgObject(chatId, HI.get("uz"));
            bot.executeMessage(sendMessage);
        }
    }

    private void sendAuthorizeMessage() {
        SendPhoto photo = ePhoto(chatId, """
                <b>Assalomu alaykum 😊</b>

                <i>Dazaning monitoring botiga xush kelibsiz!
                Botga a'zo bo'lish uchun Qo'shilish tugmasini bosing</i>""", "src/main/resources/logo.png");
        photo.setReplyMarkup(InlineBoards.join());
        bot.sendPhoto(photo);
    }

    private static final MessageHandler instance = new MessageHandler();

    public static MessageHandler getInstance() {
        return instance;
    }
}
