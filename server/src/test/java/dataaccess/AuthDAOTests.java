package dataaccess;

import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

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
        dao.clear();
        AuthData auth = dao.createAuth("user1");
        Assertions.assertDoesNotThrow(() -> dao.deleteSession(auth.getAuthToken()));
    }

    @Test
    public void deleteSessionNegative() throws DataAccessException {
        dao.clear();
        AuthData auth = dao.createAuth("user1");
        Assertions.assertDoesNotThrow(() -> dao.deleteSession(null));
    }

    @Test
    public void clear() throws DataAccessException {
        Assertions.assertDoesNotThrow(() -> dao.clear());
    }
}
