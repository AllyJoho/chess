package dataaccess;

import model.AuthData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class MySqlAuthDAO extends AuthDAO {
    public MySqlAuthDAO() {
        try {
            String[] createStatements = {
            """
                CREATE TABLE IF NOT EXISTS  sessions (
                    token varchar(256) NOT NULL,
                    username varchar(256) NOT NULL,
                    PRIMARY KEY (token)
                ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
            };
            DatabaseManager.configureDatabase(createStatements);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public AuthData createAuth(String username) throws DataAccessException{
        String authToken = generateToken();
        AuthData session = new AuthData(authToken, username);
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = conn.prepareStatement("INSERT INTO sessions values (?, ?)")) {
                preparedStatement.setString(1, authToken);
                preparedStatement.setString(2, username);
                preparedStatement.executeUpdate();
                return session;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public AuthData getSession(String token) throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM sessions where token = ?")) {
                preparedStatement.setString(1, token);
                ResultSet rs = preparedStatement.executeQuery();
                if(rs.next()){
                    String resultToken = rs.getString("token");
                    String resultUsername = rs.getString("username");
                    return new AuthData(resultToken, resultUsername);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void deleteSession(String token) throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = conn.prepareStatement("DELETE FROM sessions WHERE token=?")) {
                preparedStatement.setString(1, token);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void clear() throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = conn.prepareStatement("TRUNCATE sessions")) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }
    private static String generateToken() {
        return UUID.randomUUID().toString();
    }

}