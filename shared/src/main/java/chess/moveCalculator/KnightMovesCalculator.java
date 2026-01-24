package chess.moveCalculator;

import chess.*;

import java.util.Collection;

public class KnightMovesCalculator extends ChessMovesCalculator {

    public KnightMovesCalculator(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        super(pieceColor, type);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = getSideways();
        moves = doNotLandOnTeammate(moves);
        return moves;
    }
}