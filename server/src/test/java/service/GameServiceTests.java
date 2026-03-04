package service;

import dataaccess.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import request.RegisterRequest;

public class GameServiceTests {
    GameDAO gameDataAccess = new MemoryGameDAO();
    GameService gameService = new GameService(gameDataAccess);

    @Test
    void listGamesPositive() {
    }

    @Test
    void listGamesNegative() {
    }

    @Test
    void createGamePositive() {
    }

    @Test
    void createGameNegative() {
    }

    @Test
    void joinGamePositive() {
    }

    @Test
    void joinGameNegative() {
    }
}
