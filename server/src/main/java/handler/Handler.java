package handler;

import com.google.gson.Gson;
import io.javalin.http.Context;
import request.*;
import result.RegisterResult;
import service.UserService;

public class Handler {
    public void register(Context ctx){
        var serializer = new Gson();
        RegisterRequest request = serializer.fromJson(ctx.body(), RegisterRequest.class);
        UserService register = new UserService();
        RegisterResult result = register.register(request);
        String json = serializer.toJson(result);
        ctx.status(200);
        ctx.json(json);
    }
}
