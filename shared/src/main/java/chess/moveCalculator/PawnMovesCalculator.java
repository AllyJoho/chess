package chess.moveCalculator;

import chess.*;

import java.util.Collection;
import java.util.List;

public class PawnMovesCalculator extends ChessMovesCalculator {

    public PawnMovesCalculator(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        super(pieceColor, type);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int x = myPosition.getColumn();
        int y = myPosition.getRow();
        Collection<ChessMove> moves = new java.util.ArrayList<>(List.of());
        if(pieceColor == ChessGame.TeamColor.WHITE){
            ChessPosition front = new ChessPosition(x, y+1);
            if(board.getPiece(front) == null){
                moves.add(new ChessMove(myPosition, front, null));
            }
        }
        return moves;
    }
}
