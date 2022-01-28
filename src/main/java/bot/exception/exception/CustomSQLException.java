package bot.exception.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomSQLException extends RuntimeException {
    private String friendlyMessage;

    public CustomSQLException(String message, Throwable cause) {
        super(message, cause);
        initMessage();
    }

    private void initMessage() {
        this.friendlyMessage = null;
        String message = super.getMessage();
        String systemMessage = message.trim();
        try {
            friendlyMessage = systemMessage.substring(systemMessage.lastIndexOf("ERROR: ") + 7, systemMessage.indexOf("Where")).trim();
            if (friendlyMessage.isEmpty()) {
                friendlyMessage = systemMessage.substring(systemMessage.lastIndexOf("detail: ") + 8, systemMessage.indexOf("hint: ")).trim();
            }
            if (friendlyMessage.isEmpty()) {
                friendlyMessage = systemMessage.substring(systemMessage.lastIndexOf("hint: ") + 6).trim();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
