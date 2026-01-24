package chess.moveCalculator;

import chess.*;

import java.util.Collection;
import java.util.List;

public class ChessMovesCalculator {
    protected final ChessGame.TeamColor pieceColor;
    protected final ChessPiece.PieceType type;

    public ChessMovesCalculator(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    private Collection<ChessMove> moveDirection(ChessBoard board,
                                                ChessPosition myPosition,
                                                int rDirection,
                                                int cDirection){
        Collection<ChessMove> moves = new java.util.ArrayList<>(List.of());
        int row = myPosition.getRow()+rDirection;
        int col = myPosition.getColumn()+cDirection;
        while (row > 0 && row <= 8 && col > 0 && col <= 8){
            ChessPosition pos = new ChessPosition(row,col);
            ChessPiece piece = board.getPiece(pos);
            if (piece == null) {
                moves.add(new ChessMove(myPosition, pos, null));
            } else if (piece.getTeamColor() != pieceColor) {
                moves.add(new ChessMove(myPosition, pos, null));
                break;
            } else{
                break;
            }
            if(type == ChessPiece.PieceType.KING){
                break;
            }
            row += rDirection;
            col += cDirection;
        }
        return moves;
    }

    public Collection<ChessMove> getDiagonal(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new java.util.ArrayList<>(List.of());
        moves.addAll(moveDirection(board, myPosition, 1, 1));
        moves.addAll(moveDirection(board, myPosition, -1, -1));
        moves.addAll(moveDirection(board, myPosition, 1, -1));
        moves.addAll(moveDirection(board, myPosition, -1, 1));
        return moves;
    }

    public Collection<ChessMove> getSideways(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new java.util.ArrayList<>(List.of());
        moves.addAll(moveDirection(board, myPosition, 1, 0));
        moves.addAll(moveDirection(board, myPosition, -1, 0));
        moves.addAll(moveDirection(board, myPosition, 0, -1));
        moves.addAll(moveDirection(board, myPosition, 0, 1));
        return moves;
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return List.of();
    }
}

