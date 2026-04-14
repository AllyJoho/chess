package server;

import dataaccess.*;
import handler.Handler;
import handler.WebsocketHandler;
import io.javalin.*;
import io.javalin.websocket.WsMessageHandler;
import org.jetbrains.annotations.NotNull;

public class Server {

    private final Javalin javalin;

    public Server() {
        UserDAO userDataAccess = new MySqlUserDAO();
        AuthDAO authDataAccess = new MySqlAuthDAO();
        GameDAO gameDataAccess = new MySqlGameDAO();
        Handler handler = new Handler(userDataAccess, authDataAccess, gameDataAccess);
        WebsocketHandler wsHandler = new WebsocketHandler();
        javalin = Javalin.create(config -> config.staticFiles.add("web"))
                .post("/user", handler::register)
                .post("/session", handler::login)
                .delete("/session", handler::logout)
                .get("/game", handler::listGames)
                .post("/game", handler::createGame)
                .put("/game", handler::joinGame)
                .delete("/db", handler::clear)
                .ws("/ws", (wsConfig) -> {
                    wsConfig.onConnect(ctx -> {
                        ctx.enableAutomaticPings();
                        System.out.println("Websocket connected");
                    });
                    wsConfig.onMessage(wsHandler);
                    wsConfig.onClose(_ -> System.out.println("Websocket closed"));
                });
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
