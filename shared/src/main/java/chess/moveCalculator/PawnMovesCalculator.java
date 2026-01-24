package chess.moveCalculator;

import chess.*;
import chess.ChessGame.*;
import chess.ChessPiece.*;

import java.util.Collection;
import java.util.List;

public class PawnMovesCalculator extends ChessMovesCalculator {
    Collection<ChessMove> moves;

    public PawnMovesCalculator(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        super(pieceColor, type);
    }

    private void addPromotionMoves(ChessPosition myPosition, ChessPosition newPosition, int y1){
        if(y1 == 8 || y1 == 1){
            moves.add(new ChessMove(myPosition, newPosition, PieceType.QUEEN));
            moves.add(new ChessMove(myPosition, newPosition, PieceType.BISHOP));
            moves.add(new ChessMove(myPosition, newPosition, PieceType.KNIGHT));
            moves.add(new ChessMove(myPosition, newPosition, PieceType.ROOK));
        }else{
            moves.add(new ChessMove(myPosition, newPosition, null));
        }
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        int col = myPosition.getColumn();
        int row = myPosition.getRow();
        moves = new java.util.ArrayList<>(List.of());
        int row1 = pieceColor == TeamColor.WHITE ? row+1 : row-1;
        if (row1 < 1 || row1 > 8) return moves;
        ChessPosition front = new ChessPosition(row1, col);
        if(board.getPiece(front) == null){
            System.out.println("DEBUG: Adding forward move for " + pieceColor + " Pawn at [" + col + "," + row1 + "]");
            System.out.println("DEBUG: Target square [" + row1 + "," + row1 + "] contains: allegedly nothing");
            addPromotionMoves(myPosition, front, row1);
//            if((row == 2 && pieceColor == TeamColor.WHITE) || (row == 7 && pieceColor == TeamColor.BLACK)){
//                pieceMoves(board, front);
//            }
        }
        if(col != 1){
            ChessPosition leftDiagonal = new ChessPosition(row1, col-1);
            ChessPiece ldPiece = board.getPiece(leftDiagonal);
            if(ldPiece != null && ldPiece.getTeamColor() != pieceColor){
                System.out.println("DEBUG: Adding left diagonal move for " + pieceColor + " Pawn at [" + col + "," + row + "]");
                System.out.println("DEBUG: Target square [" + (col-1) + "," + row1 + "] contains: " + ldPiece.toString());
                addPromotionMoves(myPosition, leftDiagonal, row1);
            }
        }
        if(col != 8){
            ChessPosition rightDiagonal = new ChessPosition(row1, col+1);
            ChessPiece rdPiece = board.getPiece(rightDiagonal);
            if(rdPiece != null && rdPiece.getTeamColor() != pieceColor){
                System.out.println("DEBUG: Adding right diagonal move for " + pieceColor + " Pawn at [" + col + "," + row + "]");
                System.out.println("DEBUG: Target square [" + (col+1) + "," + row1 + "] contains: " + rdPiece.toString());
                addPromotionMoves(myPosition, rightDiagonal, row1);
            }
        }
        return moves;
    }
}
