package bot.repository;

import bot.configs.DbConfig;
import bot.models.Requests;
import bot.models.User;
import bot.security.SecurityHolder;
import bot.security.Session;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Types;
import java.util.List;
import java.util.Objects;

public class AuthRepository extends BaseRepository {

    DbConfig config = DbConfig.getInstance();

    public Session register(User user) {
        String data = gson.toJson(user);
        prepareArguments(data);
        String givenData = (String) callProcedure(config.get("user.register"), Types.VARCHAR);
        return gson.fromJson(givenData, Session.class);
    }

    public User getByUserId(Long phone) {
        prepareArguments(phone);
        String givenData = (String) callProcedure(config.get("user.get.by.user_id"), Types.VARCHAR);
        return gson.fromJson(givenData, User.class);
    }

    public User getByPhone(String phone) {
        prepareArguments(phone);
        String givenData = (String) callProcedure(config.get("user.get.by.phone"), Types.VARCHAR);
        return gson.fromJson(givenData, User.class);
    }

    public void add_worker(String phone) {
        prepareArguments(phone);
        callProcedure(config.get("add.worker"), Types.BOOLEAN);
    }

    public void delete_worker(String phone) {
        prepareArguments(phone);
        callProcedure(config.get("delete.worker"), Types.BOOLEAN);
    }

    public void block_worker(String phone) {
        prepareArguments(phone);
        callProcedure(config.get("block.worker"), Types.BOOLEAN);
    }

    public boolean isReal(String phoneNumber) {
        User user = getByPhone(phoneNumber);
        return Objects.nonNull(user) && user.getPhoneNumber().equals(phoneNumber);
    }

    public List<Requests> getReqs() {
        Type type = new TypeToken<List<Requests>>() {
        }.getType();
        prepareArguments("null");
        String data = (String) callProcedure(config.get("request.list"), Types.VARCHAR);
        return gson.fromJson(data, type);
    }

    public List<User> userList() {
        Type type = new TypeToken<List<User>>() {
        }.getType();
        prepareArguments("null");
        String data = (String) callProcedure(config.get("user.list"), Types.VARCHAR);
        return gson.fromJson(data, type);
    }

    public void seeReqs(Long id) {
        prepareArguments(id);
        callProcedure(config.get("see.request"), Types.BOOLEAN);
    }

    public void sendRequest(String username, String phoneNumber,Long userId) {
        prepareArguments(username, phoneNumber,userId);
        callProcedure(config.get("add.request"), Types.BOOLEAN);
    }

    private static final AuthRepository instance = new AuthRepository();

    public static AuthRepository getInstance() {
        return instance;
    }
}
