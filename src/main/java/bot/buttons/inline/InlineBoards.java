package bot.buttons.inline;

import bot.models.Group;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import bot.localization.Words;
import bot.utils.Emojis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class InlineBoards {
    private static final InlineKeyboardMarkup board = new InlineKeyboardMarkup();

    public static InlineKeyboardMarkup join() {
        InlineKeyboardButton uz = new InlineKeyboardButton(Emojis.eJOIN + " Qo'shilish");
        uz.setCallbackData("join");
        board.setKeyboard(List.of(getRow(uz)));
        return board;
    }

    public static InlineKeyboardButton close() {
        InlineKeyboardButton close = new InlineKeyboardButton("Berkitish 🔙");
        close.setCallbackData("close");
        return close;
    }

    public static InlineKeyboardButton accept() {
        InlineKeyboardButton close = new InlineKeyboardButton("Tasdiqlash 🔰");
        close.setCallbackData("accept");
        return close;
    }

    public static InlineKeyboardMarkup closeMarkup() {
        InlineKeyboardButton close = close();
        board.setKeyboard(List.of(getRow(close)));
        return board;
    }

    public static InlineKeyboardMarkup prepareButtons(List<Group> groupList) {
        List<InlineKeyboardButton> btns = new ArrayList<>();
        for (Group group : groupList) {
            InlineKeyboardButton btn = new InlineKeyboardButton(group.getName());
            btn.setCallbackData(group.getGroupId().toString());
            btns.add(btn);
        }
        board.setKeyboard(getDualList(btns));
        return board;
    }

    public static InlineKeyboardMarkup prepareToAccept(List<Group> groupList) {
        List<InlineKeyboardButton> btns = new ArrayList<>();
        for (Group group : groupList) {
            InlineKeyboardButton btn = new InlineKeyboardButton();
            btn.setCallbackData(group.getGroupId().toString());
            btn.setText(group.getName());
            btns.add(btn);
        }
        board.setKeyboard(getDualListToAccept(btns));
        return board;
    }

    private static List<InlineKeyboardButton> getRow(InlineKeyboardButton... buttons) {
        return Arrays.stream(buttons).toList();
    }

    private static List<List<InlineKeyboardButton>> getDualList(List<InlineKeyboardButton> buttons) {
        List<List<InlineKeyboardButton>> btns = new ArrayList<>();
        for (int i = 0; i < buttons.size(); i += 2) {
            if (i + 1 < buttons.size()) {
                btns.add(List.of(buttons.get(i), buttons.get(i + 1)));
            } else {
                btns.add(List.of(buttons.get(i)));
            }
        }
        btns.add(List.of(close()));
        return btns;
    }

    private static List<List<InlineKeyboardButton>> getDualListToAccept(List<InlineKeyboardButton> buttons) {
        List<List<InlineKeyboardButton>> btns = new ArrayList<>();
        for (int i = 0; i < buttons.size(); i += 2) {
            if (i + 1 < buttons.size()) {
                btns.add(List.of(buttons.get(i), buttons.get(i + 1)));
            } else {
                btns.add(List.of(buttons.get(i)));
            }
        }
        btns.add(List.of(accept()));
        return btns;
    }
}
