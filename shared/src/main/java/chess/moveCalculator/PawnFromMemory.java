package chess.moveCalculator;

import chess.*;

import java.io.Console;
import java.util.ArrayList;
import java.util.Collection;

public class PawnFromMemory extends ChessMovesCalculator{
    private Collection<ChessMove> moves;
    public PawnFromMemory(ChessGame.TeamColor color, ChessPiece.PieceType type){
        super(color, type);
    }
    private void addPotentialMoves(ChessPosition oldPos, ChessPosition newPos, int row){
        if(row == 1 || row == 8){
            moves.add(new ChessMove(oldPos, newPos, ChessPiece.PieceType.QUEEN));
            moves.add(new ChessMove(oldPos, newPos, ChessPiece.PieceType.KNIGHT));
            moves.add(new ChessMove(oldPos, newPos, ChessPiece.PieceType.BISHOP));
            moves.add(new ChessMove(oldPos, newPos, ChessPiece.PieceType.ROOK));
        }else{
            moves.add(new ChessMove(oldPos, newPos, null));
        }
    }
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPos){
        int row = myPos.getRow();
        int col = myPos.getColumn();
        moves = new ArrayList<>();
        int row1 = pieceColor == ChessGame.TeamColor.WHITE ? row+1 : row-1;
        for (int col1 = col-1; col1 <= col+1; col1++) {
            if(col1 < 1 || col1 > 8) continue;
            ChessPosition newPos = new ChessPosition(row1, col1);
            ChessPiece newPiece = board.getPiece(newPos);
            if(col == col1){
                if(newPiece == null){
                    addPotentialMoves(myPos, newPos, row1);
                    if((row == 2 && pieceColor == ChessGame.TeamColor.WHITE) || (row == 7 && pieceColor == ChessGame.TeamColor.BLACK)){
                        int row2 = pieceColor == ChessGame.TeamColor.WHITE ? row+2 : row-2;
                        if(board.getPiece(new ChessPosition(row2, col)) == null){
                            addPotentialMoves(myPos, new ChessPosition(row2, col), row1);
                        }
                    }
                }
            } else if (newPiece != null && pieceColor != newPiece.getTeamColor()) {

                addPotentialMoves(myPos, newPos, row1);
            }
        }
        return moves;
    }
}
