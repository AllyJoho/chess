package client;

import org.junit.jupiter.api.*;
import server.Server;
import server.ServerFacade;
import request.*;
import result.*;

public class ServerFacadeTests {

    private static Server server;
    private static ServerFacade facade;

    @BeforeAll
    public static void init() {
        server = new Server();
        var port = server.run(0);
        System.out.println("Started test HTTP server on " + port);
        facade = new ServerFacade("http://localhost:" + port);
    }

    @AfterAll
    static void stopServer() {
        server.stop();
    }

    @Test
    void registerPositive() throws Exception {
        facade.clear();
        RegisterRequest request = new RegisterRequest("player1", "password", "p1@email.com");
        RegisterResult authData = facade.register(request);
        Assertions.assertTrue(authData.authToken().length() > 10);
    }

    @Test
    void registerNegative() throws Exception {
        facade.clear();
        RegisterRequest request = new RegisterRequest("player1", "password", "p1@email.com");
        facade.register(request);
        RegisterRequest request1 = new RegisterRequest("player1", "password", "p1@email.com");
        Assertions.assertThrows(Throwable.class, () -> facade.register(request1));
    }

    @Test
    void loginPositive() throws Exception {
        facade.clear();
        RegisterRequest request = new RegisterRequest("player1", "password", "p1@email.com");
        RegisterResult authData = facade.register(request);
        LogoutRequest request1 = new LogoutRequest(authData.authToken());
        facade.logout(request1, authData.authToken());
        LoginRequest request2 = new LoginRequest("player1", "password");
        Assertions.assertDoesNotThrow(() -> facade.login(request2));
    }

    @Test
    void loginNegative() throws Exception {
        facade.clear();
        LoginRequest request = new LoginRequest("player1", "password");
        Assertions.assertThrows(Throwable.class, () -> facade.login(request));
    }

    @Test
    void logoutPositive() throws Exception {
        facade.clear();
        RegisterRequest request = new RegisterRequest("player1", "password", "p1@email.com");
        RegisterResult authData = facade.register(request);
        LogoutRequest request1 = new LogoutRequest(authData.authToken());
        Assertions.assertDoesNotThrow(() -> facade.logout(request1, authData.authToken()));
    }

    @Test
    void logoutNegative() throws Exception {
        facade.clear();
        LogoutRequest request = new LogoutRequest("authData.authToken()");
        Assertions.assertDoesNotThrow(() -> facade.logout(request, null));
    }

    @Test
    void listPositive() throws Exception {
        facade.clear();
        RegisterRequest request = new RegisterRequest("player1", "password", "p1@email.com");
        RegisterResult authData = facade.register(request);
        CreateGameRequest newGame = new CreateGameRequest("game");
        facade.createGame(newGame, authData.authToken());
        CreateGameRequest newGame1 = new CreateGameRequest("game1");
        facade.createGame(newGame1, authData.authToken());
        Assertions.assertEquals(2, facade.listGames(authData.authToken()).games().size());
    }

    @Test
    void listNegative() throws Exception {
        facade.clear();
        RegisterRequest request = new RegisterRequest("player1", "password", "p1@email.com");
        RegisterResult authData = facade.register(request);
        Assertions.assertTrue(facade.listGames(authData.authToken()).games().isEmpty());
    }

    @Test
    void createPositive() throws Exception {
        facade.clear();
        RegisterRequest request = new RegisterRequest("player1", "password", "p1@email.com");
        RegisterResult authData = facade.register(request);
        CreateGameRequest newGame = new CreateGameRequest("game");
        Assertions.assertDoesNotThrow(() -> facade.createGame(newGame, authData.authToken()));
    }

    @Test
    void createNegative() throws Exception {
        facade.clear();
        RegisterRequest request = new RegisterRequest("player1", "password", "p1@email.com");
        RegisterResult authData = facade.register(request);
        CreateGameRequest newGame = new CreateGameRequest(null);
        Assertions.assertThrows(Throwable.class, () -> facade.createGame(newGame, authData.authToken()));
    }

    @Test
    void joinPositive() throws Exception {
        facade.clear();
        RegisterRequest request = new RegisterRequest("player1", "password", "p1@email.com");
        RegisterResult authData = facade.register(request);
        CreateGameRequest newGame = new CreateGameRequest("game");
        CreateGameResult result = facade.createGame(newGame, authData.authToken());
        JoinGameRequest request1 = new JoinGameRequest("WHITE", result.gameID(), "player1");
        Assertions.assertDoesNotThrow(() -> facade.joinGame(request1, authData.authToken()));
    }

    @Test
    void joinNegative() throws Exception {
        facade.clear();
        RegisterRequest request = new RegisterRequest("player1", "password", "p1@email.com");
        RegisterResult authData = facade.register(request);
        CreateGameRequest newGame = new CreateGameRequest("game");
        CreateGameResult result = facade.createGame(newGame, authData.authToken());
        JoinGameRequest request4 = new JoinGameRequest("WHITE", result.gameID(), "player1");
        facade.joinGame(request4, authData.authToken());
        LogoutRequest request1 = new LogoutRequest(authData.authToken());
        facade.logout(request1, authData.authToken());
        RegisterRequest request2 = new RegisterRequest("player2", "password", "p1@email.com");
        facade.register(request2);
        JoinGameRequest request3 = new JoinGameRequest("WHITE", result.gameID(), "player2");
        Assertions.assertThrows(Throwable.class, () -> facade.joinGame(request3, authData.authToken()));
    }

    @Test
    void clearPositive() throws Exception {
        Assertions.assertDoesNotThrow(() -> facade.clear());
    }

    @Test
    void clearNegative() throws Exception {
        facade.clear();
        RegisterRequest request = new RegisterRequest("player1", "password", "p1@email.com");
        RegisterResult authData = facade.register(request);
        CreateGameRequest newGame = new CreateGameRequest("game");
        facade.createGame(newGame, authData.authToken());
        facade.clear();
        Assertions.assertThrows(Throwable.class, () -> facade.listGames(authData.authToken()));
    }
}
