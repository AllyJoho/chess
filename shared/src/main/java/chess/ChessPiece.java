package chess;
import chess.move.*;
import chess.move.ChessMovesCalculator;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;
    private final ChessMovesCalculator movesCalculator;
    // To be accessed for Castling and En Passant. Still figuring out going to prioritize project.
    private boolean moved;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
        this.moved = false;
        movesCalculator = switch (type) {
            case KING -> new KingMovesCalculator(pieceColor, type);
            case QUEEN -> new QueenMoveCalculator(pieceColor, type);
            case BISHOP -> new BishopMovesCalculator(pieceColor, type);
            case KNIGHT -> new KnightMovesCalculator(pieceColor, type);
            case ROOK -> new RookMovesCalculator(pieceColor, type);
            case PAWN -> new PawnMovesCalculator(pieceColor, type);
        };
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    public boolean getIfMoved() {
        return moved;
    }

    public void recordMoved() {
        this.moved = true;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return movesCalculator.pieceMoves(board,myPosition);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) o;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    public String toString() {
        String letter = switch (type) {
            case KING -> "K";
            case QUEEN -> "Q";
            case BISHOP -> "B";
            case KNIGHT -> "N";
            case ROOK -> "R";
            case PAWN -> "P";
        };

        return pieceColor == ChessGame.TeamColor.WHITE ? letter : letter.toLowerCase();
    }
}
