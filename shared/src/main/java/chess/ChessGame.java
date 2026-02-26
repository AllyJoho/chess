package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor teamTurn;
    private ChessBoard board;

    public ChessGame() {
        this.teamTurn = TeamColor.WHITE;
        this.board = new ChessBoard();
        this.board.resetBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        Collection<ChessMove> possibleMoves = new ArrayList<>();
        ChessPiece startPiece = this.board.getPiece(startPosition);
        for(ChessMove move : startPiece.pieceMoves(this.board, startPosition)){
            if(!simulateMove(move, startPiece.getTeamColor())){
                possibleMoves.add(move);
            }
        }
        return possibleMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        ChessPosition startPosition = move.getStartPosition();
        ChessPosition endPosition = move.getEndPosition();
        TeamColor nextColor = getTeamTurn() == TeamColor.WHITE ? TeamColor.BLACK : TeamColor.WHITE;
        ChessPiece piece = this.board.getPiece(startPosition);
        if (piece == null) {
            throw new InvalidMoveException("No piece");
        }
        if (!piece.pieceMoves(this.board, startPosition).contains(move)) {
            throw new InvalidMoveException("Move not valid");
        }
        if(piece.getTeamColor() != getTeamTurn()){
            throw new InvalidMoveException("Not your turn");
        }
        if (!this.validMoves(startPosition).contains(move)) {
            throw new InvalidMoveException("Can't move in check");
        }
        if(move.getPromotionPiece() != null){
            piece = new ChessPiece(getTeamTurn(), move.getPromotionPiece());
        }
        this.board.addPiece(endPosition, piece);
        this.board.removePiece(startPosition);
        setTeamTurn(nextColor);
    }

    public Collection<ChessMove> teamMoves(TeamColor teamColor) {
        Collection<ChessPosition> piecesPositions = board.getTeamPositions(teamColor);
        Collection<ChessMove> possibleMoves = new ArrayList<>();
        for (ChessPosition pos : piecesPositions){
            possibleMoves.addAll(this.board.getPiece(pos).pieceMoves(this.board,pos));
        }
        return possibleMoves;
    }

    // Simulates a moves and returns if the result results in check or not
    public boolean simulateMove(ChessMove move, TeamColor pieceColor) {
        ChessBoard actualBoard = new ChessBoard(this.board);
        ChessPiece piece = this.board.getPiece(move.getStartPosition());
        this.board.addPiece(move.getEndPosition(), piece);
        this.board.removePiece(move.getStartPosition());
        boolean result = isInCheck(pieceColor);
        this.setBoard(actualBoard);
        return result;
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        ChessPosition kingPosition = this.board.getKingPosition(teamColor);
        TeamColor enemy = teamColor == TeamColor.WHITE ? TeamColor.BLACK : TeamColor.WHITE;
        Collection<ChessMove> possibleMoves = teamMoves(enemy);
        for (ChessMove move : possibleMoves){
            if((move.getEndPosition()).equals(kingPosition)) {
                return true;
            }
        }
        return false;
    }

    private boolean endGameCondition(TeamColor teamColor, boolean checkmate){
        boolean isCheckingCheck = checkmate == isInCheck(teamColor);
        if(isCheckingCheck){
            Collection<ChessPosition> piecesPositions = board.getTeamPositions(teamColor);
            Collection<ChessMove> possibleMoves = new ArrayList<>();
            for (ChessPosition pos : piecesPositions){
                possibleMoves.addAll(validMoves(pos));
            }
            return possibleMoves.isEmpty();
        }else{
            return false;
        }
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        return endGameCondition(teamColor, true);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        return endGameCondition(teamColor, false);
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessGame chessGame = (ChessGame) o;
        return teamTurn == chessGame.teamTurn && Objects.equals(board, chessGame.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamTurn, board);
    }

    @Override
    public String toString() {
        return "ChessGame{" +
                "teamTurn=" + teamTurn +
                ", board=" + board +
                '}';
    }
}
