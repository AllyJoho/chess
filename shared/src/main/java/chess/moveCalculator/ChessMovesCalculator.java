package chess.moveCalculator;

import chess.*;

import java.util.Collection;
import java.util.List;

public class ChessMovesCalculator {
    protected final ChessGame.TeamColor pieceColor;
    protected final ChessPiece.PieceType type;

    public ChessMovesCalculator(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    public Collection<ChessMove> getDiagonal(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
    }

    public Collection<ChessMove> getSideways(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return List.of();
    }
}

