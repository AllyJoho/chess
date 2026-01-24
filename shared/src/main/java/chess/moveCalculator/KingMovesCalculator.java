package chess.moveCalculator;

import chess.*;

import java.util.Collection;

public class KingMovesCalculator extends ChessMovesCalculator {

    public KingMovesCalculator(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        super(pieceColor, type);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = getSideways();
        moves = doNotLandOnTeammate(moves);
        return moves;
    }
}