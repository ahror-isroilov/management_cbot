package bot.models;

import com.google.gson.annotations.SerializedName;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class User implements BaseEntity {
    @SerializedName("chatId")
    Long chatId;
    @SerializedName("userId")
    Long userId;
    @SerializedName("firstName")
    String firstName;
    @SerializedName("lastName")
    String lastName;
    @SerializedName("phoneNumber")
    String phoneNumber;
    @SerializedName("role")
    String role;
    @SerializedName("loggedIn")
    Boolean loggedIn;
    @SerializedName("blocked")
    Boolean blocked;
    @SerializedName("deleted")
    Boolean deleted;

    @Builder(builderMethodName = "childBuilder")
    private User(Long chatId, Long userId, String firstName, String lastName, String phoneNumber, String role, Boolean loggedIn, Boolean blocked, Boolean deleted) {
        this.chatId = chatId;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.role = role;
        this.loggedIn = loggedIn;
        this.deleted = deleted;
    }
}
