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
    }

    @Test
    void loginPositive() {
    }

    @Test
    void loginNegative() {
    }

    @Test
    void logoutPositive() {
    }

    @Test
    void logoutNegative() {
    }

    @Test
    void clear() {
    }
}