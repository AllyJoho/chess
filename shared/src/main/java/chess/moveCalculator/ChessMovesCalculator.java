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

    public Collection<ChessMove> doNotLandOnTeammate(Collection<ChessMove> moves) {
        throw new RuntimeException("Not implemented");
    }

    public Collection<ChessMove> getDiagonal() {
        throw new RuntimeException("Not implemented");
    }

    public Collection<ChessMove> getSideways() {
        throw new RuntimeException("Not implemented");
    }

    public Collection<ChessMove> getKnightCorners() {
        throw new RuntimeException("Not implemented");
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return List.of();
    }
}

