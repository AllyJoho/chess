package chess;
import java.util.Arrays;
import java.util.Objects;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {

    private ChessPiece[][] squares;;
    public ChessBoard() {
        squares = new ChessPiece[8][8];
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

    private void addSquare(int x, ChessPiece.PieceType type){
        addPiece(new ChessPosition(1,x+1), new ChessPiece(ChessGame.TeamColor.WHITE, type));
        addPiece(new ChessPosition(8,x+1), new ChessPiece(ChessGame.TeamColor.BLACK, type));
        addPiece(new ChessPosition(1,8-x), new ChessPiece(ChessGame.TeamColor.WHITE, type));
        addPiece(new ChessPosition(8,8-x), new ChessPiece(ChessGame.TeamColor.BLACK, type));
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        ChessGame.TeamColor white = ChessGame.TeamColor.WHITE;
        ChessGame.TeamColor black = ChessGame.TeamColor.BLACK;
        //Pawns First
        for (int i = 1; i <= 8; i++) {
            addPiece(new ChessPosition(2,i), new ChessPiece(white, ChessPiece.PieceType.PAWN));
            addPiece(new ChessPosition(7,i), new ChessPiece(black, ChessPiece.PieceType.PAWN));
        }
        //Army
        addSquare(0, ChessPiece.PieceType.ROOK);
        addSquare(1, ChessPiece.PieceType.KNIGHT);
        addSquare(2, ChessPiece.PieceType.BISHOP);
        //Royalty
        addPiece(new ChessPosition(1,5), new ChessPiece(white, ChessPiece.PieceType.KING));
        addPiece(new ChessPosition(1,4), new ChessPiece(white, ChessPiece.PieceType.QUEEN));
        addPiece(new ChessPosition(8,5), new ChessPiece(black, ChessPiece.PieceType.KING));
        addPiece(new ChessPosition(8,4), new ChessPiece(black, ChessPiece.PieceType.QUEEN));
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
        for (int r = 8; r >= 1; r--) {
            s.append("|");
            for (int c = 1; c <= 8; c++) {
                String piece = getPiece(new ChessPosition(r, c)) == null ? " ": getPiece(new ChessPosition(r, c)).toString();
                s.append(piece);
                s.append("|");
            }
            s.append("\n");
        }
        return s.toString();
    }
}
//package chess;
//
//import java.util.Arrays;
//import java.util.Objects;
//
///**
// * A chessboard that can hold and rearrange chess pieces.
// * <p>
// * Note: You can add to this class, but you may not alter
// * signature of the existing methods.
// */
//public class ChessBoard {
//    ChessPiece[][] squares;
//    public ChessBoard() {
//        squares = new ChessPiece[8][8];
//    }
//
//    /**
//     * Adds a chess piece to the chessboard
//     *
//     * @param position where to add the piece to
//     * @param piece    the piece to add
//     */
//    public void addPiece(ChessPosition position, ChessPiece piece) {
//        squares[position.getRow()-1][position.getColumn()-1] = piece;
//    }
//
//    /**
//     * Gets a chess piece on the chessboard
//     *
//     * @param position The position to get the piece from
//     * @return Either the piece at the position, or null if no piece is at that
//     * position
//     */
//    public ChessPiece getPiece(ChessPosition position) {
//        return squares[position.getRow()-1][position.getColumn()-1];
//    }
//
//    private void addSquare(int offset, ChessPiece.PieceType type){
//        squares[0][offset] = new ChessPiece(ChessGame.TeamColor.WHITE, type);
//        squares[7][offset] = new ChessPiece(ChessGame.TeamColor.BLACK, type);
//        squares[0][7-offset] = new ChessPiece(ChessGame.TeamColor.WHITE, type);
//        squares[7][7-offset] = new ChessPiece(ChessGame.TeamColor.BLACK, type);
//    }
//
//    /**
//     * Sets the board to the default starting board
//     * (How the game of chess normally starts)
//     */
//    public void resetBoard() {
//        squares = new ChessPiece[8][8];
//        for (int i = 0; i < 8; i++) {
//            squares[1][i] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
//            squares[6][i] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
//        }
//        addSquare(0, ChessPiece.PieceType.ROOK);
//        addSquare(1, ChessPiece.PieceType.KNIGHT);
//        addSquare(2, ChessPiece.PieceType.BISHOP);
//        squares[0][3] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
//        squares[7][3] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
//        squares[0][4] = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
//        squares[7][4] = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (o == null || getClass() != o.getClass()) {
//            return false;
//        }
//        ChessBoard that = (ChessBoard) o;
//        return Objects.deepEquals(squares, that.squares);
//    }
//
//    @Override
//    public int hashCode() {
//        return Arrays.deepHashCode(squares);
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder s = new StringBuilder();
//        for (int i = 0; i < 8; i++) {
//            for (int j = 0; j < 8; j++) {
//                s.append(squares[i][j] != null ? squares[i][j].toString() : " ").append("|");
//            }
//            s.append("\n");
//        }
//        return s.toString();
//    }
//}
