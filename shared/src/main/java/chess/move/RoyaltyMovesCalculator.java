package chess.move;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;

public class RoyaltyMovesCalculator extends ChessMovesCalculator {
    Collection<ChessMove> moves;

    public RoyaltyMovesCalculator(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        super(pieceColor, type);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        moves = new ArrayList<>();
        Collection<ChessMove> moves = getSideways(board, myPosition);
        moves.addAll(getDiagonal(board, myPosition));
        return moves;
    }
}