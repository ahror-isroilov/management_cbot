package bot.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomSQLException extends RuntimeException {
    private String friendlyMessage;

    public CustomSQLException(String message) {
        super(message);
        initMessage();
    }

    private void initMessage() {
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
