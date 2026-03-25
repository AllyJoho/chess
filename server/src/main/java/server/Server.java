package server;

import dataaccess.*;
import handler.Handler;
import io.javalin.*;

public class Server {

    private final Javalin javalin;

    public Server() {
        UserDAO userDataAccess = new MySqlUserDAO();
        AuthDAO authDataAccess = new MySqlAuthDAO();
        GameDAO gameDataAccess = new MySqlGameDAO();
        Handler handler = new Handler(userDataAccess, authDataAccess, gameDataAccess);
        javalin = Javalin.create(config -> config.staticFiles.add("web"))
                .post("/user", handler::register)
                .post("/session", handler::login)
                .delete("/session", handler::logout)
                .get("/game", handler::listGames)
                .post("/game", handler::createGame)
                .put("/game", handler::joinGame)
                .delete("/db", handler::clear);
    }

    public int run(int desiredPort) {
        javalin.start(desiredPort);
        return javalin.port();
    }

    public void stop() {
        javalin.stop();
    }
}
