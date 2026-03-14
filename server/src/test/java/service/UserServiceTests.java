package service;

import dataaccess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import request.*;
import result.*;

class UserServiceTests {
    UserDAO userDataAccess = new MySqlUserDAO();
    AuthDAO authDataAccess = new MySqlAuthDAO();
    GameDAO gameDataAccess = new MySqlGameDAO();
    UserService userService = new UserService(userDataAccess, authDataAccess, gameDataAccess);

    @Test
    void registerPositive() throws DataAccessException {
        userService.clear();
        RegisterRequest request = new RegisterRequest("username", "password", "email");
        Assertions.assertDoesNotThrow(() -> userService.register(request));
    }

    @Test
    void registerNegative() throws DataAccessException {
        userService.clear();
        RegisterRequest request = new RegisterRequest("username", "password", "email");
        Assertions.assertDoesNotThrow(() -> userService.register(request));
        RegisterRequest request1 = new RegisterRequest("username", "password", "email");
        Assertions.assertThrows(DataAccessException.class, () -> userService.register(request1));
    }

    @Test
    void loginPositive() throws DataAccessException {
        userService.clear();
        RegisterRequest setup = new RegisterRequest("username", "password", "email");
        Assertions.assertDoesNotThrow(() -> userService.register(setup));
        LoginRequest request = new LoginRequest("username", "password");
        Assertions.assertDoesNotThrow(() -> userService.login(request));
    }

    @Test
    void loginNegative() throws DataAccessException {
        userService.clear();
        LoginRequest request = new LoginRequest("username", "password");
        Assertions.assertThrows(DataAccessException.class, () -> userService.login(request));
    }

    @Test
    void logoutPositive() throws DataAccessException {
        userService.clear();
        RegisterRequest setup = new RegisterRequest("username", "password", "email");
        RegisterResult authData = userService.register(setup);
        LogoutRequest request = new LogoutRequest(authData.authToken());
        Assertions.assertDoesNotThrow(() -> userService.logout(request));
    }

    @Test
    void logoutNegative() throws DataAccessException {
        userService.clear();
        LogoutRequest request = new LogoutRequest("hahaha");
        Assertions.assertThrows(DataAccessException.class, () -> userService.logout(request));
    }

    @Test
    void clear() throws DataAccessException {
        Assertions.assertDoesNotThrow(() -> userService.clear());
    }
}