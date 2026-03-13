package dataaccess;

import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.util.HashMap;
import java.util.Objects;

import java.sql.*;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
import static java.sql.Types.NULL;

public class MySqlUserDAO extends UserDAO{
    public MySqlUserDAO() throws DataAccessException {
        configureDatabase();
    }
    public void example() throws Exception {
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM users where username = ?")) {
                preparedStatement.setString(1, u.getUsername());
                ResultSet rs = preparedStatement.executeQuery();
                rs.next();
                rs.getString("username");
                System.out.println(rs.getInt(1));
            }
        }
    }
    final private HashMap<String, UserData> users = new HashMap<>();
    public UserData getUser(String username, String password) throws DataAccessException{
//        if(!users.containsKey(username)){
//            throw new DataAccessException("unauthorized");
//        }
//        UserData user = users.get(username);
//        if(!Objects.equals(user.getPassword(), password)){
//            throw new DataAccessException("unauthorized");
//        }
//        return user;
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM users where username = ?")) {
                preparedStatement.setString(1, username);
                ResultSet rs = preparedStatement.executeQuery();
                rs.next();
                rs.getString("username");
                System.out.println(rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public UserData createUser(UserData u) throws DataAccessException{
        if(getUser(u.getUsername(), u.getUsername()) != null){
            throw new DataAccessException("already taken");
        }
        try (var conn = DatabaseManager.getConnection()) {
            try (var preparedStatement = conn.prepareStatement("INSERT INTO users values (?, ?, ?)")) {
                preparedStatement.setString(1, u.getUsername());
                preparedStatement.setString(2, u.getEmail());
                String hashedPassword = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());
                preparedStatement.setString(3, hashedPassword);
                preparedStatement.executeUpdate();
                return u;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void clear() throws DataAccessException{
        users.clear();
    }

//    private int getQuery(String statement) throws DataAccessException, SQLException {
//        try (var conn = DatabaseManager.getConnection()) {
//            try (var preparedStatement = conn.prepareStatement(statement)) {
//                var rs = preparedStatement.executeQuery();
//                rs.next();
//                System.out.println(rs.getInt(1));
//            }
//        }
//    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  users (
              username varchar(256) NOT NULL,
              email varchar(256) NOT NULL,
              password_hash CHAR(60) NOT NULL,
              PRIMARY KEY (username)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (Connection conn = DatabaseManager.getConnection()) {
            for (String statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }
}