package bot.localization;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Languages {
    UZ("uz"),
    RU("ru"),
    EN("en"),
    DEFAULT("none");
    private final String code;
}
