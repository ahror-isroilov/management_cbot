package bot.localization;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Languages {
    UZ("O'zbek"),
    RU("Русский"),
    EN("English"),
    DEFAULT("none");
    private final String code;
}
