package bot.handlers;

import bot.buttons.inline.InlineBoards;
import bot.buttons.markup.MarkupBoards;
import bot.models.Group;
import bot.models.Requests;
import bot.models.User;
import bot.processors.AuthProcessor;
import bot.states.ActionState;
import bot.states.State;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendLocation;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;

import java.util.List;
import java.util.function.Predicate;

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
            SendMessage sendMessage = msgObject(chatId, """
                    <i>
                    Ushbu bot daza.uz kompaniyasining sotuv ishlarini monitoring qilish uchun yaratilgan.
                    Kompaniya haqida ma'lumot olish uchun saytga tashrif buyuring</i>""");
            bot.executeMessage(sendMessage);
            SendLocation location = new SendLocation();
            location.setChatId(chatId.toString());
            location.setLatitude(40.3455741);
            location.setLongitude(70.7930828);
            bot.sendLocation(location);
        } else if (hasLoggedIn(chatId) && !isWorker(chatId) && !isAdmin(chatId) && message.hasText() && mText.equals("/request")) {
            User user = repository.getByUserId(message.getFrom().getId());
            repository.sendRequest(user.getFirstName(), user.getPhoneNumber(), user.getUserId());
            bot.executeMessage(sendMessage = msgObject(chatId, "<b>So'rovingiz yuborildi ✅</b>"));
        } else if (hasLoggedIn(chatId) && isAdmin(chatId) && message.hasText() && mText.equals("Faollik \uD83D\uDD05")) {
            sendMessage = msgObject(chatId, "<b>Faollik</b>");
            sendMessage.setReplyMarkup(MarkupBoards.activity());
            bot.executeMessage(sendMessage);
        } else if (hasLoggedIn(chatId) && isAdmin(chatId) && message.hasText() && mText.equals("So'rovlar 📬")) {
            List<Requests> reqs = repository.getReqs();
            if (reqs.size() < 1) {
                sendMessage = msgObject(chatId, "<b>So'nggi so'rovlar mavjud emas</b>");
                bot.executeMessage(sendMessage);
                return;
            }
            executeRequests(reqs);
        } else if (hasLoggedIn(chatId) && isAdmin(chatId) && message.hasText() && mText.equals("Ishchilar 👷‍♂️")) {
            List<User> userList = repository.userList();
            if (userList.size() < 1) {
                sendMessage = msgObject(chatId, "<b>Aktiv foydalanuvchilar mavjud emas</b>");
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
                sendMessage = msgObject(chatId, "<b>Guruhlarni faollashtirish uchun  ularning ustiga bosing</b>");
                sendMessage.setReplyMarkup(InlineBoards.prepareButtons(unAcceptedList));
            }
            else sendMessage = msgObject(chatId, "<b>Barcha guruhlar faollashtirilgan</b>");
            bot.executeMessage(sendMessage);
        } else if (hasLoggedIn(chatId) && isAdmin(chatId) && message.hasText() && mText.equals("Guruhni o'chirish ➖")) {
            if (acceptedList.size() > 0)
                sendMessage = msgObject(chatId, "<b>Guruhlarni olib tashlash uchun ularni ustiga bosing</b>");
            else sendMessage = msgObject(chatId, "<b>Faol guruhlar mavjud emas</b>");
            sendMessage.setReplyMarkup(InlineBoards.prepareButtons(acceptedList));
            bot.executeMessage(sendMessage);
        } else if (hasLoggedIn(chatId) && isAdmin(chatId) && message.hasText() && mText.equals("Guruhlar 👥")) {
            if (acceptedList.size() > 0) sendMessage = msgObject(chatId, prepareText(acceptedList));
            else sendMessage = msgObject(chatId, "<b>Faol guruhlar mavjud emas</b>");
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
            sendMessage = msgObject(chatId, """
                    <b>Qo'shmoqchi bo'lgan ishchingizning telefon raqamini quyidagi formatda kiriting 👇</b>                 
                    <u>998001234567</u>
                    """);
            bot.executeMessage(sendMessage);
        } else if (hasLoggedIn(chatId) && isAdmin(chatId) && state.getState(chatId).equals(ActionState.ADD_WORKER.getCode())) {
            String phone = message.getText();
            if (validPhone(phone)) {
                if (!repository.isReal(phone)) {
                    sendMessage = msgObject(chatId, "<b>Bu raqam bilan botga hech kim azo bo'lmagan</b>");
                    bot.executeMessage(sendMessage);
                    return;
                }
                repository.add_worker(phone);
                sendMessage = msgObject(chatId, "<b>Muvaffaqiyatli qo'shildi ✅</b>");
                bot.executeMessage(sendMessage);
                User user1 = repository.getByPhone(phone);
                sendMessage = msgObject(user1.getChatId(), "<b>Assalomu alaykum</b>\n<i>Admin tomonidan qabul qilindingiz!\nIshni boshlash uchun /start bosing</i>");
                state.setState(chatId, ActionState.DEFAULT.getCode());
                bot.executeMessage(sendMessage);
            } else {
                sendMessage = msgObject(chatId, "<b>Iltimos to'g'ri raqam kiriting</b>");
                bot.executeMessage(sendMessage);
            }
        } else if (hasLoggedIn(chatId) && isAdmin(chatId) && message.hasText() && mText.equals("Ishchini bloklash ⛔️")) {
            state.setState(chatId, ActionState.DELETE_WORKER.getCode());
            sendMessage = msgObject(chatId, """
                    <b>Bloklamoqchi bo'lgan ishchingizning telefon raqamini quyidagi formatda kiriting 👇</b>            
                    <u>998001234567</u>
                    """);
            bot.executeMessage(sendMessage);
        } else if (hasLoggedIn(chatId) && isAdmin(chatId) && state.getState(chatId).equals(ActionState.DELETE_WORKER.getCode())) {
            String phone = message.getText();
            if (validPhone(phone)) {
                if (!repository.isReal(phone)) {
                    sendMessage = msgObject(chatId, "<b>Bu raqam bilan foydalanuvchi topilmadi</b>");
                    bot.executeMessage(sendMessage);
                    return;
                }
                repository.block_worker(phone);
                sendMessage = msgObject(chatId, "<b>Foydalanuvchi bloklandi ✅</b>");
                bot.executeMessage(sendMessage);
                User user1 = repository.getByPhone(phone);
                sendMessage = msgObject(user1.getChatId(), "<b>Admin tomonidan bloklandingiz ⛔️</b>");
                state.setState(chatId, ActionState.DEFAULT.getCode());
                bot.executeMessage(sendMessage);
            } else {
                sendMessage = msgObject(chatId, "<b>Noto'g'ri raqam</b>");
                bot.executeMessage(sendMessage);
            }
        } else if (hasLoggedIn(chatId) && isWorker(chatId) && message.hasText() && mText.equals("Yuborish 📨")) {
            state.setState(chatId, ActionState.SEND_MESSAGE.getCode());
            sendMessage = msgObject(chatId, "<b>Guruhga yuborish uchun kerakli ma'lumotlarni jo'nating</b>");
            sendMessage.setReplyMarkup(MarkupBoards.back());
            bot.executeMessage(sendMessage);
        } else if (hasLoggedIn(chatId) && isWorker(chatId) && state.getState(chatId).equals(ActionState.SEND_MESSAGE.getCode())) {
            prepareDocument(update);
            state.setState(chatId, ActionState.SEND_MESSAGE.getCode());
        } else {
            if (chatId > 0) {
                sendMessage = msgObject(chatId, "<b>Kechirasiz! tushunmadim :(</b>");
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
            document.setDocument(new InputFile(doc.getFileId()));
            document.setParseMode("HTML");
            document.setCaption("<b>Yuboruvchi: %s </b>".formatted(fromName));
            for (Group group : groupList) {
                document.setChatId(group.getGroupId().toString());
                bot.send(document);
            }
            sendMessage = msgObject(chatId, "<b>Xabaringiz yuborildi ✅</b>");
            sendMessage.setReplyMarkup(MarkupBoards.back());
            bot.executeMessage(sendMessage);
            return;
        } else if (update.getMessage().hasPhoto()) {
            List<PhotoSize> messagePhoto = message.getPhoto();
            SendPhoto photo = new SendPhoto();
            String fromName = message.getFrom().getFirstName();
            photo.setPhoto(new InputFile(messagePhoto.get(3).getFileId()));
            photo.setParseMode("HTML");
            photo.setCaption("<b>Yuboruvchi: %s </b>".formatted(fromName));
            for (Group group : groupList) {
                photo.setChatId(group.getGroupId().toString());
                bot.sendPhoto(photo);
            }
            sendMessage = msgObject(chatId, "<b>Xabaringiz yuborildi ✅</b>");
            sendMessage.setReplyMarkup(MarkupBoards.back());
            bot.executeMessage(sendMessage);
            return;
        } else if (update.getMessage().hasText()) {
            String text = message.getText();
            String fromName = message.getFrom().getFirstName();
            for (Group group : groupList) {
                sendMessage = msgObject(group.getGroupId(), "<b>Yuboruvchi: %s</b>\n%s".formatted(fromName, text));
                bot.executeMessage(sendMessage);
            }
            sendMessage = msgObject(chatId, "<b>Xabaringiz yuborildi ✅</b>");
            sendMessage.setReplyMarkup(MarkupBoards.back());
            bot.executeMessage(sendMessage);
            return;
        }
        sendMessage = msgObject(chatId, "<b>Xabaringiz yuborilmadi ❌</b>");
        sendMessage.setReplyMarkup(MarkupBoards.back());
        bot.executeMessage(sendMessage);
    }

    private void printUsers(List<User> userList) {
        StringBuilder sb = new StringBuilder();
        sb.append("<b>Ayni vaqtda ishchi vazifasidagi va bloklanmagan foydalanuvchilar ro'yxati\n\n</b>");
        int count = 1;
        for (User user1 : userList) {
            sb.append("<b>%s. Tahallus :<a href=\"tg://user?id=%s\">%s</a>\n⠷ Telefon: <code>%s</code></b>\n\n".formatted(count, user1.getUserId(), user1.getFirstName(), user1.getPhoneNumber()));
            count++;
        }
        sendMessage = msgObject(chatId, sb.toString());
        bot.executeMessage(sendMessage);
    }

    private void executeRequests(List<Requests> reqs) {
        for (Requests req : reqs) {
            sendMessage = msgObject(chatId, "<b>Yuboruvchi: <a href=\"tg://user?id=%s\">%s</a>\nTelefon raqam: </b><code>%s</code>\n<b>Yuborildi: %s</b>".formatted(req.getUserId(), req.getUsername(), req.getPhoneNumber(), req.getCreatedAt().substring(0, 19)));

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
            sendMessage = msgObject(chatId, "<b>Hayrli kun</b>");
            sendMessage.setReplyMarkup(MarkupBoards.adminMenu());
            bot.executeMessage(sendMessage);
        } else if (isWorker(user.getId())) {
            sendMessage = msgObject(chatId, "<b>Hayrli kun</b>");
            sendMessage.setReplyMarkup(MarkupBoards.workerMenu());
            bot.executeMessage(sendMessage);
        } else {
            sendMessage = msgObject(chatId, "<b>Hayrli kun</b>");
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
