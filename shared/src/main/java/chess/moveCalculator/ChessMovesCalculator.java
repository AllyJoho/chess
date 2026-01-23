package chess.moveCalculator;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

public class ChessMovesCalculator {
    protected final ChessGame.TeamColor pieceColor;
    protected final ChessPiece.PieceType type;
    protected final ChessBoard board;
    protected final ChessPosition myPosition;

    public ChessMovesCalculator(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.board = board;
        this.myPosition = myPosition;
        this.pieceColor = pieceColor;
        this.type = type;
    }
}

