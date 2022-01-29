package bot.security;

import bot.models.Group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SecurityHolder {
    public static Map<String, Session> sessions = new HashMap<>();
    public static Map<Long, List<Group>> senderGroups = new HashMap<>();

    public static Session getSessionByPhone(String phoneNumber) {
        for (Map.Entry<String, Session> entry : sessions.entrySet()) {
            if (entry.getKey().equals(phoneNumber)) return entry.getValue();
        }
        return null;
    }

    public static List<Group> getGroup(Long id) {
        for (Map.Entry<Long, List<Group>> entry : senderGroups.entrySet()) {
            if (entry.getKey().equals(id)) return entry.getValue();
        }
        return new ArrayList<>();
    }

    public static void resetGroups(Long id) {
        senderGroups.remove(id);
    }


    public static void setSession(Session sessionUser) {
        sessions.put(sessionUser.getPhoneNumber(), sessionUser);
    }

    public static void setGroup(Long userId, List<Group> groupList) {
        senderGroups.put(userId, groupList);
    }
}
