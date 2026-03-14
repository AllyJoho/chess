package dataaccess;

import model.UserData;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;

public class MySqlUserDAO extends UserDAO{
    public MySqlUserDAO() {
        try {
            configureDatabase();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public UserData getUser(String username) throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM users where username = ?")) {
                preparedStatement.setString(1, username);
                ResultSet rs = preparedStatement.executeQuery();
                if(rs.next()){
                    String resultUsername = rs.getString("username");
                    String resultEmail = rs.getString("email");
                    String resultPasswordHash = rs.getString("password_hash");
                    return new UserData(resultUsername, resultPasswordHash, resultEmail);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public boolean verifyUser(UserData u, String password){
        return BCrypt.checkpw(password, u.getPassword());
    }

    public UserData createUser(UserData u) throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO users values (?, ?, ?)")) {
                preparedStatement.setString(1, u.getUsername());
                preparedStatement.setString(2, u.getEmail());
                String hashedPassword = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());
                preparedStatement.setString(3, hashedPassword);
                preparedStatement.executeUpdate();
                return u;
            }
        } catch (SQLException e) {
            if(e.getErrorCode() == 1062){
                throw new DataAccessException("already taken");
            }
            throw new RuntimeException(e);
        }
    }

    public void clear() throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = conn.prepareStatement("TRUNCATE users")) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

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