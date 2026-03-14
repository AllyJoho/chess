package dataaccess;

import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDAOTests {
    MySqlAuthDAO dao = new MySqlAuthDAO();
//    UserData user1 = new UserData("name", "password", "email");
//    UserData user2 = new UserData("bob", "123", "haha");
//    UserData user3 = new UserData("alice", "abc", "example@email.com");

    @Test
    public void createAuthPositive() throws DataAccessException {
        dao.clear();
        AuthData auth = dao.createAuth("user1");
        Assertions.assertEquals("user1", auth.getUsername());
    }

    @Test
    public void createAuthNegative() throws DataAccessException {
        dao.clear();
        Assertions.assertThrows(RuntimeException.class, () -> dao.createAuth(null));
    }

    @Test
    public void getSessionPositive() throws DataAccessException {
        dao.clear();
        AuthData auth = dao.createAuth("user1");
        Assertions.assertDoesNotThrow(() -> dao.getSession(auth.getAuthToken()));
    }

    @Test
    public void getSessionNegative() throws DataAccessException {
        dao.clear();
        AuthData session = dao.getSession("hey");
        Assertions.assertNull(session);
    }

    @Test
    public void deleteSessionPositive() throws DataAccessException {
        dao.clear();
        AuthData auth = dao.createAuth("user1");
        Assertions.assertDoesNotThrow(() -> dao.deleteSession(auth.getAuthToken()));
    }

    @Test
    public void deleteSessionNegative() throws DataAccessException {
        dao.clear();
        dao.createAuth("user1");
        dao.createAuth("user2");
        dao.createAuth("user3");
        Assertions.assertDoesNotThrow(() -> dao.deleteSession(null));
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(*) FROM sessions")) {
                ResultSet rs = preparedStatement.executeQuery();
                rs.next();
                Assertions.assertEquals(3, rs.getInt(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void clear() throws DataAccessException {
        Assertions.assertDoesNotThrow(() -> dao.clear());
    }
}
