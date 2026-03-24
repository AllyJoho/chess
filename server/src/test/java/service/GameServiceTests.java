package service;

import dataaccess.*;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GameServiceTests {
    UserDAO userDataAccess = new MySqlUserDAO();
    AuthDAO authDataAccess = new MySqlAuthDAO();
    GameDAO gameDataAccess = new MySqlGameDAO();
    UserService userService = new UserService(userDataAccess, authDataAccess, gameDataAccess);
    GameService gameService = new GameService(gameDataAccess);

    @Test
    void listGamesNegative() throws DataAccessException {
        userService.clear();
        ListGameResult games = gameService.listGames();
        Assertions.assertEquals(0, games.games().toArray().length);
    }

    @Test
    void listGamesPositive() throws DataAccessException {
        userService.clear();
        CreateGameRequest request = new CreateGameRequest("blah");
        gameService.createGame(request);
        ListGameResult games = gameService.listGames();
        GameData firstGame = games.games().getFirst();
        Assertions.assertEquals(1, games.games().toArray().length);
        Assertions.assertEquals("blah", firstGame.getGameName());
    }

    @Test
    void createGamePositive() throws DataAccessException {
        CreateGameRequest request = new CreateGameRequest("blah");
        CreateGameResult result = gameService.createGame(request);
        Assertions.assertEquals(1, result.gameID());
    }

    @Test
    void createGameNegative() {
        CreateGameRequest request = new CreateGameRequest(null);
        Assertions.assertThrows(DataAccessException.class, () -> gameService.createGame(request));
    }

    @Test
    void joinGamePositive() throws DataAccessException {
        CreateGameRequest setup = new CreateGameRequest("blah");
        CreateGameResult result = gameService.createGame(setup);
        JoinGameRequest request = new JoinGameRequest("WHITE", result.gameID(), "player");
        Assertions.assertDoesNotThrow(() -> gameService.joinGame(request));
    }

    @Test
    void joinGameNegative() throws DataAccessException {
        CreateGameRequest setup = new CreateGameRequest("blah");
        CreateGameResult result = gameService.createGame(setup);
        JoinGameRequest request = new JoinGameRequest("HEY", result.gameID(), "player");
        Assertions.assertThrows(DataAccessException.class, () -> gameService.joinGame(request));
    }
}
