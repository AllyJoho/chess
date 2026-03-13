package service;

import dataaccess.DataAccessException;
import dataaccess.MySqlUserDAO;
import model.UserData;
import org.junit.jupiter.api.Test;

public class SqlTests {
    @Test
    public void createUser() throws DataAccessException {
        MySqlUserDAO sqlUserDAO = new MySqlUserDAO();
        sqlUserDAO.createUser(new UserData("name", "password", "email"));
    }
    @Test
    public void getUser() throws DataAccessException {
        MySqlUserDAO sqlUserDAO = new MySqlUserDAO();
        sqlUserDAO.createUser(new UserData("name", "password", "email"));
    }
}
