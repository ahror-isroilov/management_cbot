package bot.security;

import java.util.HashMap;
import java.util.Map;

public class SecurityHolder {
    public static Map<String, Session> sessions = new HashMap<>();

    public static Session getSessionByPhone(String phoneNumber) {
        for (Map.Entry<String, Session> entry : sessions.entrySet()) {
            if (entry.getKey().equals(phoneNumber)) return entry.getValue();
        }
        return null;
    }

    public static void setSession(Session sessionUser) {
        sessions.put(sessionUser.getPhoneNumber(), sessionUser);
    }
}
