package bot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import uz.jl.utils.Print;

import static uz.jl.utils.Color.GREEN;
import static uz.jl.utils.Color.RED;

public class Application {
    public static void main(String[] args) {
        connectBot();
    }

    static String statusText;

    private static void connectBot() {
        try {
            TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
            api.registerBot(new Bot());
            statusText = "Connected!";
            Print.println(GREEN, statusText);
        } catch (TelegramApiException e) {
            statusText = "Not connected!";
            Print.println(RED, statusText);
        }
        if (statusText.equals("Not connected!")) {
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            connectBot();
        }
    }
}
