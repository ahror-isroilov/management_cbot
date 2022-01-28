package bot.security;

import com.google.gson.annotations.SerializedName;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Session {
    Long chatId;
    Long userId;
    String firstName;
    String lastName;
    String phoneNumber;
    String Role;
    Boolean loggedIn;
    Boolean blocked;
    Boolean deleted;

    @Builder(builderMethodName = "childBuilder")
    private Session(Long chatId, Long userId, String firstName, String lastName, String phoneNumber, String Role, Boolean loggedIn, Boolean blocked, Boolean deleted) {
        this.chatId = chatId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.Role = Role;
        this.loggedIn = loggedIn;
        this.blocked = blocked;
        this.deleted = deleted;
    }
}
