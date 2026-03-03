package handler;

import com.google.gson.Gson;
import dataaccess.*;
import io.javalin.http.Context;
import request.*;
import result.*;
import service.*;

import java.util.Map;

public class Handler {
    private final UserService userService;
    private final AuthService authService;
    private final GameService gameService;
    private final Gson serializer;

    public Handler(UserDAO userDataAccess, AuthDAO authDataAccess, GameDAO gameDataAccess){
        this.userService = new UserService(userDataAccess, authDataAccess);
        this.authService = new AuthService(userDataAccess, authDataAccess, gameDataAccess);
        this.gameService = new GameService();
        this.serializer = new Gson();
    }
    public void clear(Context ctx){
        try {
            authService.clear();
            ctx.status(200);
        } catch (DataAccessException e) {
            var body = new Gson().toJson(Map.of("message", String.format("Error: %s", e.getMessage())));
            ctx.status(500);
            ctx.json(body);
        }
    }
    public void register(Context ctx){
        RegisterRequest request = serializer.fromJson(ctx.body(), RegisterRequest.class);
        RegisterResult result = null;
        try {
            result = userService.register(request);
            String json = serializer.toJson(result);
            ctx.status(200);
            ctx.json(json);
        } catch (DataAccessException e) {
            var body = new Gson().toJson(Map.of("message", String.format("Error: %s", e.getMessage())));
            if (e.getMessage().contains("bad request")) {
                ctx.status(400);
            } else if (e.getMessage().contains("username already exists")) {
                ctx.status(403);
            }
            ctx.json(body);
        }
    }
    public void login(Context ctx){
        LoginRequest request = serializer.fromJson(ctx.body(), LoginRequest.class);
        LoginResult result = null;
        try {
            result = userService.login(request);
            String json = serializer.toJson(result);
            ctx.status(200);
            ctx.json(json);
        } catch (DataAccessException e) {
            var body = new Gson().toJson(Map.of("message", String.format("Error: %s", e.getMessage())));
            if (e.getMessage().contains("bad request")) {
                ctx.status(400);
            } else if (e.getMessage().contains("missing username/password")) {
                ctx.status(401);
            }
            ctx.json(body);
        }
    }
    public void logout(Context ctx) {
        String authToken = authorized(ctx);
        if (authToken == null) {
            return;
        }
        LogoutRequest request = new LogoutRequest(authToken);
        try {
            userService.logout(request);
            ctx.status(200);
        } catch (DataAccessException e) {
            var body = new Gson().toJson(Map.of("message", String.format("Error: %s", e.getMessage())));
            ctx.status(500);
            ctx.json(body);
        }
    }
    public void listGames(Context ctx){
        if (authorized(ctx) == null) {
            return;
        }
    }
    public void createGame(Context ctx){
        if (authorized(ctx) == null) {
            return;
        }
    }
    public void joinGame(Context ctx) {
        if (authorized(ctx) == null) {
            return;
        }
    }
    private String authorized(Context ctx) {
        String authToken = ctx.header("Authorization");
        if (!authService.authorize(authToken)) {
            ctx.contentType("application/json");
            var body = new Gson().toJson(Map.of("message", "Error: unauthorized"));
            ctx.status(401);
            ctx.json(body);
            return null;
        }
        return authToken;
    }

}
