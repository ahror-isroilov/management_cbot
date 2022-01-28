package bot.states;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class State {
    private final static State state = new State();
    private final Map<Long, String> userState = new HashMap<>();

    public String getState(Long chatId) {
        for (Map.Entry<Long, String> entry : userState.entrySet()) {
            if (entry.getKey().equals(chatId)) return entry.getValue();
        }
        return null;
    }

    public void setState(long chatID, String newState) {
        userState.put(chatID, newState);
    }

    public String getUserState(long chatID) {
        return userState.get(chatID);
    }

    public static State getInstance() {
        return state;
    }
}
