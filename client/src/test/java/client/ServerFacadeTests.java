package client;

import org.junit.jupiter.api.*;
import server.Server;
import server.ServerFacade;
import request.*;
import result.*;

import static org.junit.jupiter.api.Assertions.assertTrue;


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
    public void sampleTest() {
        assertTrue(true);
    }

    @Test
    void register() throws Exception {
        RegisterRequest request = new RegisterRequest("player1", "password", "p1@email.com");
        RegisterResult authData = facade.register(request);
        assertTrue(authData.authToken().length() > 10);
    }

    @Test
    void clear() throws Exception {
        Assertions.assertDoesNotThrow(() -> facade.clear());
    }
}
