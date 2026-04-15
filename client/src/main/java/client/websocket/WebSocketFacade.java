package client.websocket;

import chess.ChessMove;
import client.ClientData;
import client.Repl;
import com.google.gson.Gson;

import client.ChessClient;
import jakarta.websocket.*;
import websocket.commands.*;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class WebSocketFacade extends Endpoint {

    Session session;
    Repl notificationHandler;

    public WebSocketFacade(String url, Repl notificationHandler) throws Exception {
        try {
            url = url.replace("http", "ws");
            URI socketURI = new URI(url + "/ws");
            this.notificationHandler = notificationHandler;

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            this.session = container.connectToServer(this, socketURI);

            //set message handler
            this.session.addMessageHandler(new MessageHandler.Whole<String>() {
                @Override
                public void onMessage(String message) {
                    ServerMessage notification = new Gson().fromJson(message, ServerMessage.class);
                    notificationHandler.notify(notification);
                }
            });
        } catch (DeploymentException | IOException | URISyntaxException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    //Endpoint requires this method, but you don't have to do anything
    @Override
    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }

    public void enterGame(ClientData data) throws Exception {
        try {
            var action = new ConnectAndLeaveCommand(UserGameCommand.CommandType.CONNECT,
                    data.getAuthToken(),
                    data.getGameData().getGameID(),
                    data.getUsername());
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void leaveGame(ClientData data) throws Exception {
        try {
            var action = new ConnectAndLeaveCommand(UserGameCommand.CommandType.LEAVE,
                    data.getAuthToken(),
                    data.getGameData().getGameID(),
                    data.getUsername());
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void resign(ClientData data) throws Exception {
        try {
            var action = new UserGameCommand(UserGameCommand.CommandType.RESIGN,
                    data.getAuthToken(),
                    data.getGameData().getGameID());
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new Exception(ex.getMessage());
        }
    }

    public void makeMove(ClientData data, ChessMove move) throws Exception {
        try {
            var action = new MakeMoveCommand(UserGameCommand.CommandType.MAKE_MOVE,
                    data.getAuthToken(),
                    data.getGameData().getGameID(),
                    move,
                    data.getGamePerspective(),
                    data.getGameData().getGame().getBoard());
            this.session.getBasicRemote().sendText(new Gson().toJson(action));
        } catch (IOException ex) {
            throw new Exception(ex.getMessage());
        }
    }
}