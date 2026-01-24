package chess.moveCalculator;

import chess.*;

import java.util.Collection;
import java.util.List;

public class RookMovesCalculator extends ChessMovesCalculator {
    public RookMovesCalculator(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        super(pieceColor, type);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return getSideways(board, myPosition);
    }
}
