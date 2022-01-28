package bot.models;

import lombok.*;
import org.checkerframework.checker.guieffect.qual.SafeEffect;

/**
 * @author : softi  -> @data :1/28/2022 15:02
 */
@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class Group {
    Long groupId;
    String name;
    Boolean accepted;
}