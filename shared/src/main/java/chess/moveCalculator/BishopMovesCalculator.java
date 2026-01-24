package chess.moveCalculator;

import chess.*;

import java.util.Collection;

public class BishopMovesCalculator extends ChessMovesCalculator {
    public BishopMovesCalculator(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        super(pieceColor, type);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return getDiagonal(board, myPosition);
    }
}
