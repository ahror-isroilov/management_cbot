package bot.repository;

import bot.configs.DbConfig;
import bot.exception.CustomSQLException;
import bot.utils.Utils;
import com.google.gson.Gson;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.sql.*;

public class BaseRepository {

    private Object[] args;
    protected Utils utils = Utils.getInstance();
    protected Gson gson = this.utils.getGson();
    protected Connection connection;

    {
        try {
            connection = DriverManager.getConnection(DbConfig.getInstance().get("db.jdbc"), DbConfig.getInstance().get("db.username"), DbConfig.getInstance().get("db.password"));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected Serializable callProcedure(String query, int outType) {
        try {
            CallableStatement statement = connection.prepareCall(query);
            prepareToExecute(statement);
            ResultSet resultSet = statement.executeQuery();
            return prepareResultSet(resultSet, outType);
        } catch (SQLException ex) {
            throw new CustomSQLException(ex.getMessage());
        }
    }

    @SneakyThrows
    private Serializable prepareResultSet(ResultSet resultSet, int outType) {
        if (resultSet.next()) {
            return switch (outType) {
                case Types.VARCHAR -> resultSet.getString(1);
                case Types.BIGINT -> resultSet.getLong(1);
                case Types.BOOLEAN -> resultSet.getBoolean(1);
                case -1 -> -1;
                default -> throw new IllegalStateException("Unexpected value: " + outType);
            };
        }
        return null;
    }

    @SneakyThrows
    private void prepareToExecute(CallableStatement statement) {
        for (int i = 0; i < this.args.length; i++) {
            statement.setObject(i + 1, args[i]);
        }
    }

    protected void prepareArguments(Object... args) {
        this.args = args;
    }
}
