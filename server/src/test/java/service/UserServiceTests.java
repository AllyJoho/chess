package service;

import dataaccess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import request.*;
import result.*;

class UserServiceTests {
    UserDAO userDataAccess = new MemoryUserDAO();
    AuthDAO authDataAccess = new MemoryAuthDAO();
    GameDAO gameDataAccess = new MemoryGameDAO();
    UserService userService = new UserService(userDataAccess, authDataAccess, gameDataAccess);

    @Test
    void registerPositive() {
        RegisterRequest request = new RegisterRequest("username", "password", "email");
        Assertions.assertDoesNotThrow(() -> userService.register(request));
    }

    @Test
    void registerNegative() {
        RegisterRequest request = new RegisterRequest("username", "password", "email");
        Assertions.assertDoesNotThrow(() -> userService.register(request));
        RegisterRequest request1 = new RegisterRequest("username", "password", "email");
        Assertions.assertThrows(DataAccessException.class, () -> userService.register(request1));
    }

    @Test
    void loginPositive() {
        RegisterRequest setup = new RegisterRequest("username", "password", "email");
        Assertions.assertDoesNotThrow(() -> userService.register(setup));
        LoginRequest request = new LoginRequest("username", "password");
        Assertions.assertDoesNotThrow(() -> userService.login(request));
    }

    @Test
    void loginNegative() {
    }

    @Test
    void logoutPositive() throws DataAccessException {
        RegisterRequest setup = new RegisterRequest("username", "password", "email");
        RegisterResult authData = userService.register(setup);
        LogoutRequest request = new LogoutRequest(authData.authToken());
        Assertions.assertDoesNotThrow(() -> userService.logout(request));
    }

    @Test
    void logoutNegative() {
        LogoutRequest request = new LogoutRequest("hahaha");
        Assertions.assertThrows(DataAccessException.class, () -> userService.logout(request));
    }

    @Test
    void clear() throws DataAccessException {
        RegisterRequest setup = new RegisterRequest("username", "password", "email");
        userService.register(setup);
        LoginRequest request = new LoginRequest("username", "password");
        userService.clear();
        Assertions.assertThrows(DataAccessException.class, () -> userService.login(request));
    }
}