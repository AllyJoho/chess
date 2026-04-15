package handler;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import com.google.gson.Gson;
import dataaccess.*;
import io.javalin.websocket.WsMessageContext;
import io.javalin.websocket.WsMessageHandler;
import model.AuthData;
import model.GameData;
import model.UserData;
import org.jetbrains.annotations.NotNull;
import websocket.commands.ConnectAndLeaveCommand;
import websocket.commands.MakeMoveCommand;
import websocket.commands.UserGameCommand;
import websocket.messages.ServerMessage;

import java.io.IOException;
import java.util.Collection;

public class WebsocketHandler implements WsMessageHandler {
    private final ConnectionManager connections = new ConnectionManager();
    private final ServerMessage.ServerMessageType notifType = ServerMessage.ServerMessageType.NOTIFICATION;
    private final ServerMessage.ServerMessageType errorType = ServerMessage.ServerMessageType.ERROR;
    private final ServerMessage.ServerMessageType gameType = ServerMessage.ServerMessageType.LOAD_GAME;
    private final UserDAO userDAO = new MySqlUserDAO();
    private final AuthDAO authDAO = new MySqlAuthDAO();
    private final GameDAO gameDAO = new MySqlGameDAO();

    @Override
    public void handleMessage(@NotNull WsMessageContext ctx) throws Exception {
        Gson gson = new Gson();
        UserGameCommand cmd = gson.fromJson(ctx.message(), UserGameCommand.class);
        switch (cmd.getCommandType()){
            case CONNECT -> connect(ctx, cmd);
            case LEAVE -> leave(ctx, gson.fromJson(ctx.message(), ConnectAndLeaveCommand.class));
            case MAKE_MOVE -> makeMove(ctx, gson.fromJson(ctx.message(), MakeMoveCommand.class));
            case RESIGN -> resign(ctx, cmd);
        }
    }

    private void connect(WsMessageContext ctx, UserGameCommand cmd) {
        connections.addPlayer(ctx.session, cmd.getGameID());
        try {
            String username = authDAO.getSession(cmd.getAuthToken()).getUsername();
            var message = String.format("%s joined the game", username);
            var notification = new ServerMessage(notifType, message, null);
            if(loadGame(ctx, cmd, username)){
                connections.broadcast(cmd.getGameID(), ctx.session, notification);
            }
        } catch (Exception e) {
            var message = "Can't find user";
            var notification = new ServerMessage(errorType, null, message);
            connections.userNotify(ctx.session, notification);
        }

    }

    private boolean loadGame(WsMessageContext ctx, UserGameCommand cmd, String username){
        try {
            GameData gameData = gameDAO.getGame(cmd.getGameID());
            int gamePerspective;
            if(gameData.getWhiteUsername().equals(username)){
                gamePerspective = 1;
            } else if (gameData.getBlackUsername().equals(username)) {
                gamePerspective = 2;
            } else {
                gamePerspective = 3;
            }
            var notification = new ServerMessage(gameType, gameData.getGame().getBoard(), gamePerspective);
            connections.userNotify(ctx.session, notification);
            return true;
        } catch (Exception e) {
            var message = "Can't find game";
            var notification = new ServerMessage(errorType, null, message);
            connections.userNotify(ctx.session, notification);
            return false;
        }
    }

    private void leave(WsMessageContext ctx, UserGameCommand cmd) {
        connections.removePlayer(cmd.getGameID(), ctx.session);
        try {
            GameData gameData = gameDAO.getGame(cmd.getGameID());
            String username = authDAO.getSession(cmd.getAuthToken()).getUsername();
            String whiteUsername = gameData.getWhiteUsername();
            String blackUsername =  gameData.getBlackUsername();
            if(username.equals(gameData.getWhiteUsername())){
                whiteUsername = null;
            } else if (username.equals(gameData.getBlackUsername())){
                blackUsername = null;
            }
            var message = String.format("%s left the game", username);
            var notification = new ServerMessage(notifType, message, null);
            connections.broadcast(cmd.getGameID(), ctx.session, notification);
            gameData = new GameData(gameData.getGameID(),
                    whiteUsername,
                    blackUsername,
                    gameData.getGameName(),
                    gameData.getGame());
            gameDAO.updateGame(gameData);
        } catch (Exception e) {
            var message = "Error leaving game";
            var notification = new ServerMessage(errorType, null, message);
            connections.userNotify(ctx.session, notification);
        }
    }

    private void makeMove(WsMessageContext ctx, MakeMoveCommand cmd) {
        try {
            GameData gameData = gameDAO.getGame(cmd.getGameID());
            String username = authDAO.getSession(cmd.getAuthToken()).getUsername();
            int gamePerspective;
            if(gameData.getWhiteUsername().equals(username)){
                gamePerspective = 1;
                if(gameData.getGame().getTeamTurn().equals(ChessGame.TeamColor.BLACK)){
                    throw new Exception("Not your turn!");
                }
            } else if (gameData.getBlackUsername().equals(username)) {
                gamePerspective = 2;
                if(gameData.getGame().getTeamTurn().equals(ChessGame.TeamColor.WHITE)){
                    throw new Exception("Not your turn!");
                }
            } else {
                gamePerspective = 3;
            }
            checkMove(cmd.getMove(), gameData, gamePerspective);
            var notification = new ServerMessage(gameType, gameData.getGame().getBoard(), gamePerspective);
            connections.broadcast(gameData.getGameID(), null, notification);
            String message = String.format("%s made move %s", username, cmd.getMove().toString());
            notification = new ServerMessage(notifType, message, null);
            connections.broadcast(gameData.getGameID(), ctx.session, notification);
        } catch (Exception e) {
//            var message = "Can't find game";
            var notification = new ServerMessage(errorType, null, e.getMessage());
            connections.userNotify(ctx.session, notification);
        }
    }

    private void checkMove(ChessMove move, GameData gameData, int gamePerspective) throws Exception {
        ChessPosition startPos = move.getStartPosition();
        ChessPosition endPos = move.getEndPosition();
        ChessGame game = gameData.getGame();
        if (game.getGameState() != 0){
            throw new Exception("The game is over");
        }
        if(gamePerspective == 3){
            throw new Exception("You're observing and can't play the game");
        }
        Collection<ChessMove> moves =  game.validMoves(startPos);
        Collection<ChessMove> filteredMoves = moves.stream()
                .filter(e -> e.getStartPosition().equals(startPos))
                .filter(e -> e.getEndPosition().equals(endPos))
                .toList();
        if(!filteredMoves.contains(move)){
            throw new Exception("Move not valid");
        }
        game.makeMove(move);
        if (game.isInCheckmate(ChessGame.TeamColor.BLACK)){
            var message = String.format("%s is in checkmate", gameData.getBlackUsername());
            game.setGameState(2);
            var notification = new ServerMessage(notifType, message, null);
            connections.broadcast(gameData.getGameID(), null, notification);
        } else if (game.isInCheckmate(ChessGame.TeamColor.WHITE)){
            var message = String.format("%s is in checkmate", gameData.getWhiteUsername());
            game.setGameState(2);
            var notification = new ServerMessage(notifType, message, null);
            connections.broadcast(gameData.getGameID(), null, notification);
        } else if (game.isInStalemate(ChessGame.TeamColor.WHITE) || game.isInStalemate(ChessGame.TeamColor.BLACK)) {
            var message = "The game is in stalemate";
            game.setGameState(3);
            var notification = new ServerMessage(notifType, message, null);
            connections.broadcast(gameData.getGameID(), null, notification);
        }
        gameData = new GameData(gameData.getGameID(),
                gameData.getWhiteUsername(),
                gameData.getBlackUsername(),
                gameData.getGameName(),
                game);
        gameDAO.updateGame(gameData);
    }

    private void resign(WsMessageContext ctx, UserGameCommand cmd) throws IOException {
        try {
            GameData gameData = gameDAO.getGame(cmd.getGameID());
            String username = authDAO.getSession(cmd.getAuthToken()).getUsername();
            if (gameData.getGame().getGameState() != 0){
                throw new Exception("The game is over");
            }
            int gamePerspective;
            if(gameData.getWhiteUsername().equals(username)){
                gamePerspective = 1;
            } else if (gameData.getBlackUsername().equals(username)) {
                gamePerspective = 2;
            } else {
                gamePerspective = 3;
            }
            if (gamePerspective == 3){
                throw new Exception("You're observing, sit down");
            }
            gameData.getGame().setGameState(1);
            gameData = new GameData(gameData.getGameID(),
                    gameData.getWhiteUsername(),
                    gameData.getBlackUsername(),
                    gameData.getGameName(),
                    gameData.getGame());
            gameDAO.updateGame(gameData);
            var message = String.format("%s resigned", username);
            var notification = new ServerMessage(notifType, message, null);
            connections.broadcast(gameData.getGameID(), null, notification);
        } catch (Exception e) {
            var notification = new ServerMessage(errorType, null, e.getMessage());
            connections.userNotify(ctx.session, notification);
        }
    }
}
