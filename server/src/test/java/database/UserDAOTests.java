package database;

import dataaccess.DataAccessException;
import dataaccess.MySqlUserDAO;
import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserDAOTests {
    MySqlUserDAO dao = new MySqlUserDAO();
    UserData user1 = new UserData("name", "password", "email");
    UserData user2 = new UserData("bob", "123", "haha");
    UserData user3 = new UserData("alice", "abc", "example@email.com");
    @Test
    public void createUserPositive() throws DataAccessException {
        dao.clear();
        Assertions.assertDoesNotThrow(() -> dao.createUser(user1));
    }

    @Test
    public void createUserNegative() throws DataAccessException {
        dao.clear();
        dao.createUser(user1);
        Assertions.assertThrows(DataAccessException.class, () -> dao.createUser(user1));
    }

    @Test
    public void getUserPositive() throws DataAccessException {
        dao.clear();
        dao.createUser(user1);
        UserData user = dao.getUser("name");
        Assertions.assertEquals("email", user.getEmail());
    }

    @Test
    public void getUserNegative() throws DataAccessException {
        dao.clear();
        UserData user = dao.getUser("name");
        Assertions.assertEquals(null, user);
    }

    @Test
    public void clear() throws DataAccessException {
        Assertions.assertDoesNotThrow(() -> dao.clear());
    }
}
