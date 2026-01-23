package chess.moveCalculator;

import chess.*;

import java.util.Collection;

public class BishopMovesCalculator extends ChessMovesCalculator {

    public BishopMovesCalculator(ChessBoard board, ChessPosition myPosition, ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        super(board, myPosition, pieceColor, type);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        throw new RuntimeException("Not implemented");
    }


}
