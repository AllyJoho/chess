package server;

import dataaccess.*;
import handler.Handler;
import io.javalin.*;

public class Server {

    private final Javalin javalin;

    public Server() {
        UserDAO userDataAccess = new MemoryUserDAO();
        AuthDAO authDataAccess = new MemoryAuthDAO();
        GameDAO gameDataAccess = new MemoryGameDAO();
        Handler handler = new Handler(userDataAccess, authDataAccess, gameDataAccess);
        javalin = Javalin.create(config -> config.staticFiles.add("web"))
                .post("/user", handler::register)
                .post("/session", handler::login)
                .delete("/session", handler::logout)
                .get("/game", handler::listGames)
                .post("/game", handler::createGame)
                .put("/game", handler::joinGame)
                .delete("/db", handler::clear);

        // Register your endpoints and exception handlers here.

    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
