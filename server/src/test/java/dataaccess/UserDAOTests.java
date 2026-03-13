package dataaccess;

import model.UserData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserDAOTests {
    MySqlUserDAO dao = new MySqlUserDAO();
    UserData user = new UserData("name", "password", "email");
    UserData user1 = new UserData("name", "password", "email");
    @Test
    public void createUserPositive() throws DataAccessException {
        dao.clear();
        Assertions.assertDoesNotThrow(() -> dao.createUser(user));
    }

    @Test
    public void createUserNegative() throws DataAccessException {
        dao.clear();
        dao.createUser(user);
        Assertions.assertThrows(DataAccessException.class, () -> dao.createUser(user));
    }

    @Test
    public void getUserPositive() throws DataAccessException {
        dao.clear();
        dao.createUser(user);
        UserData user = dao.getUser("name");
        Assertions.assertEquals("email", user.getEmail());
    }

    @Test
    public void getUserNegative() throws DataAccessException {
        dao.clear();
        UserData user = dao.getUser("name");
        Assertions.assertNull(user);
    }

    @Test
    public void clear() throws DataAccessException {
        Assertions.assertDoesNotThrow(() -> dao.clear());
    }
}
