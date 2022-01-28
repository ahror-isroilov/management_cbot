package bot.buttons.markup;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.List;

public class MarkupBoards {

    private static final ReplyKeyboardMarkup board = new ReplyKeyboardMarkup();

    public static ReplyKeyboardMarkup adminMenu() {
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        row1.add(new KeyboardButton("Guruhlar 👥"));
        row1.add(new KeyboardButton("Ishchilar 👷‍♂️"));
        row2.add(new KeyboardButton("So'rovlar 📬"));
        row2.add(new KeyboardButton("Faollik 🔅"));
        board.setKeyboard(List.of(row1, row2));
        board.setResizeKeyboard(true);
        board.setSelective(true);
        return board;
    }

    public static ReplyKeyboardMarkup activity() {
        KeyboardRow row1 = new KeyboardRow();
        KeyboardRow row2 = new KeyboardRow();
        KeyboardRow row3 = new KeyboardRow();
        row1.add(new KeyboardButton("Guruhni faollashtirish 🔰"));
        row1.add(new KeyboardButton("Guruhni o'chirish ➖"));
        row2.add(new KeyboardButton("Ishchi qo'shish ➕"));
        row2.add(new KeyboardButton("Ishchini bloklash ⛔️"));
        row3.add(new KeyboardButton("Orqaga 🔙"));
        board.setKeyboard(List.of(row1, row2, row3));
        board.setResizeKeyboard(true);
        board.setSelective(true);
        return board;
    }

    public static ReplyKeyboardMarkup workerMenu() {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Yuborish 📨"));
        board.setKeyboard(List.of(row1));
        board.setResizeKeyboard(true);
        board.setSelective(true);
        return board;
    }

    public static ReplyKeyboardMarkup back() {
        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton("Orqaga 🔙"));
        board.setKeyboard(List.of(row1));
        board.setResizeKeyboard(true);
        board.setSelective(true);
        return board;
    }

    public static ReplyKeyboardMarkup phoneButton() {
        KeyboardButton phoneContact = new KeyboardButton("Raqamingizni ulashing 📞");
        phoneContact.setRequestContact(true);
        KeyboardRow row1 = new KeyboardRow();
        row1.add(phoneContact);
        board.setKeyboard(List.of(row1));
        board.setResizeKeyboard(true);
        board.setSelective(true);
        return board;
    }
}
