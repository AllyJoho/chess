package dataaccess;

import model.AuthData;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AuthDAOTests {
    MySqlAuthDAO dao = new MySqlAuthDAO();
    UserData user1 = new UserData("name", "password", "email");
    UserData user2 = new UserData("bob", "123", "haha");
    UserData user3 = new UserData("alice", "abc", "example@email.com");

    @Test
    public void createAuthPositive() throws DataAccessException {
        dao.clear();
        Assertions.assertDoesNotThrow(() -> dao.createAuth("user1"));
    }

    @Test
    public void createAuthNegative() throws DataAccessException {
        dao.clear();
        dao.createAuth("user1");
        Assertions.assertThrows(DataAccessException.class, () -> dao.createAuth("user1"));
    }

    @Test
    public void getSessionPositive() throws DataAccessException {
        dao.clear();
        AuthData auth = dao.createAuth("user1");
        Assertions.assertEquals("user1", auth.getUsername());
    }

    @Test
    public void getSessionNegative() throws DataAccessException {
        dao.clear();
//        UserData user = dao.getUser("name");
//        Assertions.assertEquals(null, user);
    }

    @Test
    public void deleteSessionPositive() throws DataAccessException {
        dao.clear();
//        dao.createUser(user1);
//        UserData user = dao.getUser("name");
//        Assertions.assertEquals("email", user.getEmail());
    }

    @Test
    public void deleteSessionNegative() throws DataAccessException {
        dao.clear();
//        UserData user = dao.getUser("name");
//        Assertions.assertEquals(null, user);
    }

    @Test
    public void clear() throws DataAccessException {
        Assertions.assertDoesNotThrow(() -> dao.clear());
    }
}
