package pd.fin.dao.user;

import pd.fin.dao.abs.DAO;
import pd.fin.entities.UserEntity;
import pd.fin.source.ConfigDataSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDAO extends DAO<UserEntity, Integer> {

    private final ConfigDataSource config;

    public UserDAO(ConfigDataSource config) {
        super(config);
        this.config = config;
    }

    @Override
    public void create(UserEntity object) {
        var query =
                """
                         INSERT INTO users (id, username, password, first_name, last_name) VALUES (?, ?, ?, ?, ?);
                        
                        """;
        try (var connection = config.getDataSource().getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, object.id());
            preparedStatement.setString(2, object.username());
            preparedStatement.setString(3, object.password());
            preparedStatement.setString(4, object.firstName());
            preparedStatement.setString(5, object.lastName());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<UserEntity> findById(Integer id) {
        var query =
                """
                                SELECT * from users where id = ?;
                        """;
        try (var connection = config.getDataSource().getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setObject(1, id);
            var resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                var user = new UserEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name")
                );
                return Optional.of(user);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return Optional.empty();
    }

    @Override
    public List<UserEntity> findAll() {
        var query =
                """
                                SELECT * from users;
                        """;
        try (var connection = config.getDataSource().getConnection();
             var preparedStatement = connection.prepareStatement(query)) {
            var resultSet = preparedStatement.executeQuery();
            var users = new ArrayList<UserEntity>();
            while (resultSet.next()) {
                var user = new UserEntity(
                        resultSet.getInt("id"),
                        resultSet.getString("username"),
                        resultSet.getString("password"),
                        resultSet.getString("first_name"),
                        resultSet.getString("last_name")
                );
                users.add(user);
            }
            return users;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
