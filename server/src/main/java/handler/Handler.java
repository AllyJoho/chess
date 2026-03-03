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
        this.gameService = new GameService(gameDataAccess);
        this.serializer = new Gson();
    }
    public void clear(Context ctx){
        try {
            authService.clear();
            ctx.status(200);
        } catch (DataAccessException e) {
            handleError(e, ctx);
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
            handleError(e, ctx);
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
            handleError(e, ctx);
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
            handleError(e, ctx);
        }
    }
    public void listGames(Context ctx){
        if (authorized(ctx) == null) {
            return;
        }
        ListGameResult result = null;
        try {
            result = gameService.listGames();
            String json = serializer.toJson(result);
            ctx.status(200);
            ctx.json(json);
        } catch (DataAccessException e) {
            handleError(e, ctx);
        }
    }
    public void createGame(Context ctx){
        if (authorized(ctx) == null) {
            return;
        }
        CreateGameRequest request = serializer.fromJson(ctx.body(), CreateGameRequest.class);
        CreateGameResult result = null;
        try {
            result = gameService.createGame(request);
            String json = serializer.toJson(result);
            ctx.status(200);
            ctx.json(json);
        } catch (DataAccessException e) {
            handleError(e, ctx);
        }
    }
    public void joinGame(Context ctx) {
        String authToken = authorized(ctx);
        if (authToken == null) {
            return;
        }
        try {
            JoinGameRequest ctxRequest = serializer.fromJson(ctx.body(), JoinGameRequest.class);
            JoinGameRequest request = new JoinGameRequest(ctxRequest.playerColor(), ctxRequest.gameID(), authService.getUser(authToken));
            gameService.joinGame(request);
            ctx.status(200);
        } catch (DataAccessException e) {
            handleError(e, ctx);
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
    private void handleError(Exception e, Context ctx){
        var body = new Gson().toJson(Map.of("message", String.format("Error: %s", e.getMessage())));
        if (e.getMessage().contains("bad request")) {
            ctx.status(400);
        } else if (e.getMessage().contains("unauthorized")) {
            ctx.status(401);
        } else if (e.getMessage().contains("already taken")) {
            ctx.status(403);
        } else {
            ctx.status(500);
        }
        ctx.json(body);
    }
}
