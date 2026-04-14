package handler;

import com.google.gson.Gson;
import io.javalin.websocket.WsMessageContext;
import io.javalin.websocket.WsMessageHandler;
import org.jetbrains.annotations.NotNull;
import websocket.commands.UserGameCommand;

public class WebsocketHandler implements WsMessageHandler {
    @Override
    public void handleMessage(@NotNull WsMessageContext ctx) throws Exception {
        Gson gson = new Gson();
        UserGameCommand comd = gson.fromJson(ctx.message(), UserGameCommand.class);
        switch (comd.getCommandType()){
            case CONNECT -> ctx.session.getRemote().sendString("connected");
            case LEAVE -> ctx.session.getRemote().sendString("leave");
            case MAKE_MOVE -> ctx.session.getRemote().sendString("move");
            case RESIGN -> ctx.session.getRemote().sendString("resign");
        }
    }

    private void connect(){

    }
}
