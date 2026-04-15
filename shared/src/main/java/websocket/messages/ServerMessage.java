package websocket.messages;


import chess.ChessBoard;
import chess.ChessGame;
import model.GameData;

import java.util.Objects;

/**
 * Represents a Message the server can send through a WebSocket
 * <p>
 * Note: You can add to this class, but you should not alter the existing
 * methods.
 */
public class ServerMessage {
    ServerMessageType serverMessageType;

    private final ChessBoard game;

    private final int gamePerspective;

    private final String message;

    private final String errorMessage;

    public enum ServerMessageType {
        LOAD_GAME,
        ERROR,
        NOTIFICATION
    }

    public ServerMessage(ServerMessageType type, String message, String errorMessage) {
        this.serverMessageType = type;
        this.game = null;
        this.message = message;
        this.errorMessage = errorMessage;
        this.gamePerspective = 0;
    }

    public ServerMessage(ServerMessageType type, ChessBoard game, int gamePerspective) {
        this.serverMessageType = type;
        this.game = game;
        this.message = null;
        this.errorMessage = null;
        this.gamePerspective = gamePerspective;
    }

    public ServerMessageType getServerMessageType() {
        return this.serverMessageType;
    }

    public ChessBoard getGame() {
        return this.game;
    }

    public String getMessage() {
        return this.message;
    }

    public String getErrorMessage() {
        return this.errorMessage;
    }

    public int getGamePerspective(){
        return this.gamePerspective;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServerMessage that)) {
            return false;
        }
        return getServerMessageType() == that.getServerMessageType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getServerMessageType());
    }
}
