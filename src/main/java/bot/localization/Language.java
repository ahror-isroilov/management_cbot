package bot.localization;

import java.util.HashMap;
import java.util.Map;

public class Language {
    private static final bot.localization.Languages sessionLang = bot.localization.Languages.DEFAULT;
    private static final Language instance = new Language();

    public static Language getInstance() {
        return instance;
    }

    private static final Map<Long, bot.localization.Languages> chatLanguages = new HashMap<>();

    public bot.localization.Languages getLangByChatId(Long chatId) {
        for (Map.Entry<Long, bot.localization.Languages> entry : chatLanguages.entrySet()) {
            if (entry.getKey().equals(chatId)) return entry.getValue();
        }
        return null;
    }

    public static void setSessionLang(Long chatId, bot.localization.Languages languages) {
        chatLanguages.put(chatId, languages);
    }
}
