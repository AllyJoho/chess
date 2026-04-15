package websocket.commands;

import chess.ChessBoard;
import chess.ChessMove;

public class MakeMoveCommand extends UserGameCommand {
    private final ChessMove move;

    private final int gamePerspective;

    private final ChessBoard board;

    public MakeMoveCommand(UserGameCommand.CommandType commandType,
                                  String authToken,
                                  Integer gameID,
                                  ChessMove move,
                                  int gamePerspective,
                                  ChessBoard board) {
        super(commandType, authToken, gameID);
        this.move = move;
        this.gamePerspective = gamePerspective;
        this.board = board;
    }

    public ChessMove getMove() {
        return move;
    }

    public int getGamePerspective(){
        return gamePerspective;
    }

    public ChessBoard getBoard(){
        return board;
    }
}