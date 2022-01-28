package bot;

import bot.handlers.UpdateHandler;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    UpdateHandler handler = UpdateHandler.getInstance();

    @Override
    public String getBotUsername() {
        return "@Emma_cbot";
    }

    @Override
    public String getBotToken() {
        return "5197444772:AAFJm_YnHf6HAGSz0VQyxy8ykzDJjcNz404";
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

    public void sendAudio(SendAudio message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendPhoto(SendPhoto photo) {
        try {
            execute(photo);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendAnimation(SendAnimation message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void sendLocation(SendLocation location) {
        try {
            execute(location);
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
