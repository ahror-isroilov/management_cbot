package bot.localization;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Words {

    //===============================>MENU_WORDS<===============================\\
    WELCOME("Assalomu alaykum", "Zdrastvyte", "Welcome");
    private final String uz;
    private final String ru;
    private final String en;

    public String get(String language) {
        if (language.equals(Languages.UZ.getCode()))
            return this.uz;
        else if (language.equals(Languages.RU.getCode()))
            return this.ru;
        else
            return this.en;
    }
}
