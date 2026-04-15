package handler;

import com.google.gson.Gson;
import io.javalin.websocket.WsMessageContext;
import io.javalin.websocket.WsMessageHandler;
import org.jetbrains.annotations.NotNull;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import java.io.IOException;

public class WebsocketHandler implements WsMessageHandler {
    private final ConnectionManager connections = new ConnectionManager();
    private final ServerMessage.ServerMessageType notifType = ServerMessage.ServerMessageType.NOTIFICATION;
    private final ServerMessage.ServerMessageType errorType = ServerMessage.ServerMessageType.ERROR;
    private final ServerMessage.ServerMessageType gameType = ServerMessage.ServerMessageType.LOAD_GAME;

    @Override
    public void handleMessage(@NotNull WsMessageContext ctx) throws Exception {
        Gson gson = new Gson();
        UserGameCommand cmd = gson.fromJson(ctx.message(), UserGameCommand.class);
        switch (cmd.getCommandType()){
            case CONNECT -> connect(ctx, cmd);
            case LEAVE -> leave(ctx, cmd);
            case MAKE_MOVE -> makeMove(ctx, cmd);
            case RESIGN -> resign(ctx, cmd);
        }
    }

    private void connect(WsMessageContext ctx, UserGameCommand cmd) throws IOException {
//        ctx.session.getRemote().sendString("connected");
        connections.addPlayer(ctx.session, cmd.getGameID());
        var message = String.format("%s joined the game", cmd.getUsername());
        var notification = new ServerMessage(notifType, null, message, null);
        connections.broadcast(cmd.getGameID(), ctx.session, notification);

    }

    private void leave(WsMessageContext ctx, UserGameCommand cmd) throws IOException {
        ctx.session.getRemote().sendString("leave");
    }

    private void makeMove(WsMessageContext ctx, UserGameCommand cmd) throws IOException {
        ctx.session.getRemote().sendString("move");
    }

    private void resign(WsMessageContext ctx, UserGameCommand cmd) throws IOException {
        ctx.session.getRemote().sendString("resign");
    }
}
