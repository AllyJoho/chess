package chess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    ChessPiece[][] squares;
    public ChessBoard() {
        squares = new ChessPiece[8][8];
    }

    // Deep Copy Constructor
    public ChessBoard(ChessBoard other) {
        this.squares = new ChessPiece[other.squares.length][];
        for (int i = 0; i < other.squares.length; i++) {
            this.squares[i] = new ChessPiece[other.squares[i].length];
            for (int j = 0; j < other.squares[i].length; j++) {
                ChessPiece piece = other.squares[i][j];
                if (piece != null) {
                    this.squares[i][j] = new ChessPiece(piece.getTeamColor(), piece.getPieceType());
                }
            }
        }
    }

    public ChessPosition getKingPosition(ChessGame.TeamColor team) {
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = this.squares[row][col];
                if (piece != null && piece.getTeamColor() == team && piece.getPieceType() == ChessPiece.PieceType.KING){
                    return new ChessPosition(row+1, col+1);
                }
            }
        }
        return null;
    }

    public Collection<ChessPosition> getTeamPositions(ChessGame.TeamColor team) {
        Collection<ChessPosition> teamPositions = new ArrayList<>();
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPiece piece = this.squares[row][col];
                if (piece != null && piece.getTeamColor() == team){
                    teamPositions.add(new ChessPosition(row+1, col+1));
                }
            }
        }
        return teamPositions;
    }

    /**
     * Removes a chess piece from the chessboard. I manually added this because idk what else to do.
     *
     * @param position where to add the piece to
     */
    public void removePiece(ChessPosition position) {
        squares[position.getRow()-1][position.getColumn()-1] = null;
    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        squares[position.getRow()-1][position.getColumn()-1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        return squares[position.getRow()-1][position.getColumn()-1];
    }

    private void addSquare(int offset, ChessPiece.PieceType type){
        squares[0][offset] = new ChessPiece(ChessGame.TeamColor.WHITE, type);
        squares[7][offset] = new ChessPiece(ChessGame.TeamColor.BLACK, type);
        squares[0][7-offset] = new ChessPiece(ChessGame.TeamColor.WHITE, type);
        squares[7][7-offset] = new ChessPiece(ChessGame.TeamColor.BLACK, type);
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        squares = new ChessPiece[8][8];
        for (int i = 0; i < 8; i++) {
            squares[1][i] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            squares[6][i] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        }
        addSquare(0, ChessPiece.PieceType.ROOK);
        addSquare(1, ChessPiece.PieceType.KNIGHT);
        addSquare(2, ChessPiece.PieceType.BISHOP);
        squares[0][3] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        squares[7][3] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        squares[0][4] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        squares[7][4] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Objects.deepEquals(squares, that.squares);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(squares);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                s.append(squares[i][j] != null ? squares[i][j].toString() : " ").append("|");
            }
            s.append("\n");
        }
        return s.toString();
    }
}
