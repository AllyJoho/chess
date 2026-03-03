package server;

import handler.Handler;
import io.javalin.*;

public class Server {

    private final Javalin javalin;

    public Server() {
        Handler handler = new Handler();
        javalin = Javalin.create(config -> config.staticFiles.add("web"))
                .post("/user", handler::register);

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
