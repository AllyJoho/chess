package handler;

import com.google.gson.Gson;
import dataaccess.*;
import io.javalin.http.Context;
import request.*;
import result.*;
import service.*;

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
            String json = serializer.toJson(e);
            ctx.status(500);
            ctx.json(json);
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
            String json = serializer.toJson(e);
            ctx.status(400);
            ctx.json(json);
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
            String json = serializer.toJson(e);
            ctx.status(400);
            ctx.json(json);
        }
    }
}
