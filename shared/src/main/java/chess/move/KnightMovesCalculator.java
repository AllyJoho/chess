package chess.move;

import chess.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class KnightMovesCalculator extends ChessMovesCalculator {
    public KnightMovesCalculator(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        super(pieceColor, type);
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        int row1;
        int col1;
        int[][] basicMoves = {{-2, -1}, {-2, 1}, {2, -1}, {2, 1}, {-1, 2}, {1, 2}, {1, -2}, {-1, -2}};
        for (int i = 0; i < 8; i++) {
            row1 = row+basicMoves[i][0];
            col1 = col+basicMoves[i][1];
            if(row1 > 0 && row1 <= 8 && col1 > 0 && col1 <= 8){
                ChessPosition pos1 = new ChessPosition(row1, col1);
                ChessPiece piece = board.getPiece(pos1);
                if (piece == null || piece.getTeamColor() != pieceColor) {
                    moves.add(new ChessMove(myPosition, pos1, null));
                }
            }
        }
        return moves;
    }
}