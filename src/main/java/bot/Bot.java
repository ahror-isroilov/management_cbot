package bot;

import bot.handlers.UpdateHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendAnimation;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    private static UpdateHandler handler = UpdateHandler.getInstance();

    @Override
    public String getBotUsername() {
        return "@management_cbot";
    }

    @Override
    public String getBotToken() {
        return "5099129358:AAHoLzNGnrNT1wrNPtT24d6pi7Xen2WY1bU";
    }

    @Override
    public void onUpdateReceived(Update update) {
        handler.handle(update);
    }

    public void executeMessage(BotApiMethod<?> message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void executeAudio(SendAudio message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void executeAnimation(SendAnimation message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void send(SendDocument sendDocument) {
        try {
            this.execute(sendDocument);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    private static final Bot instance = new Bot();

    public static Bot getInstance() {
        return instance;
    }
}
