package chess.moveCalculator;

import chess.*;

import java.util.Collection;
import java.util.List;

public class KingMovesCalculator extends ChessMovesCalculator {
    Collection<ChessMove> moves;
    
    public KingMovesCalculator(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        super(pieceColor, type);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        moves = new java.util.ArrayList<>(List.of());
        Collection<ChessMove> moves = getSideways(board, myPosition);
        moves.addAll(getDiagonal(board, myPosition));
        return moves;
    }
}