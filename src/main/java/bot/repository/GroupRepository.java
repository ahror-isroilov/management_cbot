package bot.repository;

import bot.configs.DbConfig;
import bot.models.Group;
import com.google.gson.reflect.TypeToken;
import org.glassfish.jersey.message.internal.Token;

import java.lang.reflect.Type;
import java.sql.Types;
import java.util.List;

public class GroupRepository extends BaseRepository {
    DbConfig config = DbConfig.getInstance();

    public void addGroup(Long groupId, String groupName) {
        prepareArguments(groupId, groupName);
        callProcedure(config.get("group.add"), Types.BOOLEAN);
    }

    public void delGroup(Long groupId) {
        prepareArguments(groupId);
        callProcedure(config.get("group.delete"), Types.BOOLEAN);
    }

    public void acceptGroup(Long groupId) {
        prepareArguments(groupId);
        callProcedure(config.get("group.accept"), Types.BOOLEAN);
    }

    public void unAcceptGroup(Long groupId) {
        prepareArguments(groupId);
        callProcedure(config.get("group.unaccept"), Types.BOOLEAN);
    }

    public List<Group> getAcceptedList() {
        prepareArguments("null");
        Type typeToken = new TypeToken<List<Group>>() {
        }.getType();
        String data = (String) callProcedure(config.get("group.accepted.list"), Types.VARCHAR);
        return gson.fromJson(data, typeToken);
    }

    public List<Group> getAllList() {
        prepareArguments("null");
        Type typeToken = new TypeToken<List<Group>>() {
        }.getType();
        String data = (String) callProcedure(config.get("group.all.list"), Types.VARCHAR);
        return gson.fromJson(data, typeToken);
    }

    public List<Group> getUnAcceptedList() {
        prepareArguments("null");
        Type typeToken = new TypeToken<List<Group>>() {
        }.getType();
        String data = (String) callProcedure(config.get("group.unaccepted.list"), Types.VARCHAR);
        return gson.fromJson(data, typeToken);
    }

    public Group getById(Long id) {
        prepareArguments(id);
        String data = (String) callProcedure(config.get("group.getById"), Types.VARCHAR);
        return gson.fromJson(data, Group.class);
    }

    public void updateName(String name, Long id) {
        prepareArguments(name, id);
        callProcedure(config.get("update.name"), Types.BOOLEAN);
    }

    private static final GroupRepository instance = new GroupRepository();

    public static GroupRepository getInstance() {
        return instance;
    }
}
