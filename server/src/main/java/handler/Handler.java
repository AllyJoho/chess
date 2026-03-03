package handler;

import com.google.gson.Gson;
import dataaccess.AuthDAO;
import dataaccess.DataAccessException;
import dataaccess.GameDAO;
import dataaccess.UserDAO;
import io.javalin.http.Context;
import request.*;
import result.RegisterResult;
import service.UserService;

public class Handler {
    private final UserDAO userDataAccess;
    private final AuthDAO authDataAccess;
    private final GameDAO gameDataAccess;

    public Handler(UserDAO userDataAccess, AuthDAO authDataAccess, GameDAO gameDataAccess){
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
        this.gameDataAccess = gameDataAccess;
    }
    public void register(Context ctx){
        var serializer = new Gson();
        RegisterRequest request = serializer.fromJson(ctx.body(), RegisterRequest.class);
        UserService register = new UserService(userDataAccess, authDataAccess);
        RegisterResult result = null;
        try {
            result = register.register(request);
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
