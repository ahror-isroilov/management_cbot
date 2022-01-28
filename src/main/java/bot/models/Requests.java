package bot.models;

import lombok.*;

/**
 * @author : softi  -> @data :1/28/2022 20:23
 */
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Requests {
    Long id;
    Long userId;
    String phoneNumber;
    String username;
    String createdAt;
}