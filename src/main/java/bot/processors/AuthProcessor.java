package bot.processors;

import bot.handlers.AbstractMethods;
import bot.models.User;
import bot.repository.AuthRepository;
import bot.security.SecurityHolder;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Contact;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;

import static bot.localization.Words.*;

public class AuthProcessor extends AbstractMethods implements IBaseProcessor {
    AuthRepository repository = AuthRepository.getInstance();
    public Contact contact;

    @Override
    public void process(Update update) {
        prepare(update);
        contact = message.getContact();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String phoneNumber = contact.getPhoneNumber();
        if (phoneNumber.startsWith("+")) phoneNumber = phoneNumber.substring(1);
        Long chatId = message.getChatId();
        Long userId = user.getId();
        User auth = User.childBuilder().chatId(chatId).userId(userId).firstName(firstName).lastName(lastName).phoneNumber(phoneNumber).loggedIn(true).deleted(false).build();
        SecurityHolder.setSession(repository.register(auth));
        repository.sendRequest(user.getFirstName(), contact.getPhoneNumber(), user.getId());
        SendMessage sendMessage = msgObject(chatId, CONGRATULATION.get("uz"));
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        bot.executeMessage(sendMessage);
        SendMessage sendMessage1 = msgObject(chatId, NOTIFIY.get("uz"));
        bot.executeMessage(sendMessage1);
    }

    private static final AuthProcessor instance = new AuthProcessor();

    public static AuthProcessor getInstance() {
        return instance;
    }
}
