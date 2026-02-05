package chess.move;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KingMovesCalculator extends ChessMovesCalculator {
    Collection<ChessMove> moves;
    
    public KingMovesCalculator(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        super(pieceColor, type);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        moves = new ArrayList<>();
        moves.addAll(getSideways(board, myPosition));
        moves.addAll(getDiagonal(board, myPosition));
        ChessPiece myPiece = board.getPiece(myPosition);
        if(!myPiece.getIfMoved()){
            int row = myPosition.getRow();
            int col = myPosition.getColumn();
            // Going to put castling here
        }
        return moves;
    }
}