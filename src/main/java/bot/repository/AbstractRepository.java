package bot.repository;

import bot.configs.DbConfig;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public abstract class AbstractRepository extends DbConfig {
    protected StringBuilder query = new StringBuilder();
    PreparedStatement preparedStatement = null;

    protected void executeWithout() {
        if (preparedStatement != null) {
            try {
                preparedStatement.execute();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void getPreparedStatement(Object... args) {
        try {
            preparedStatement = conn().prepareStatement(query.toString());
            prepare(preparedStatement, args);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void prepare(PreparedStatement preparedStatement, Object... args) {
        for (int i = 1; i <= args.length; i++) {
            try {
                preparedStatement.setObject(i, args[i - 1]);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
