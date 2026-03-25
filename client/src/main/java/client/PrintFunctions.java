package client;
import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.move.*;

import static ui.EscapeSequences.*;

public class PrintFunctions {
    public static void printBoard(){
        System.out.println("this is a chess board");
        ChessBoard board = new ChessBoard();
        board.resetBoard();
        printChess(board, "W");
    }

    public static void printMessage(String message, String textSettings){
        String reset = RESET_TEXT_BOLD_FAINT + RESET_TEXT_ITALIC + RESET_TEXT_UNDERLINE
                + RESET_TEXT_BLINKING + RESET_TEXT_COLOR + RESET_BG_COLOR;
        System.out.print(textSettings + message + reset);
    }

    private static void printChess(ChessBoard board, String color) {
        String infoSettings = SET_TEXT_COLOR_BLACK + SET_BG_COLOR_WHITE;
        boolean direction = color.equals("W");
        int start = direction ? 7 : 0;
        int stop = direction ? -1 : 8;
        int step = direction ? -1 : 1;

        printLetterRow(infoSettings);
        for (int i = start; i != stop; i += step) {
            printMessage(" " + (i + 1) + " ", infoSettings);
            for (int j = 0; j < 8; j++) {
                ChessPosition pos = new ChessPosition(i+1,j+1);
                ChessPiece piece = board.getPiece(pos);
                printPiece(piece, pos);
            }
            printMessage(" " + (i + 1) + " " + RESET_BG_COLOR + "\n", infoSettings);
        }
        printLetterRow(infoSettings);
    }

    private static void printPiece(ChessPiece piece, ChessPosition pos){
        String pieceChar = EMPTY;
        if(piece != null){
            boolean isWhite = piece.getTeamColor().equals(ChessGame.TeamColor.WHITE);
            pieceChar = switch (piece.getPieceType()) {
                case KING -> isWhite ? WHITE_KING : BLACK_KING;
                case QUEEN -> isWhite ? WHITE_QUEEN : BLACK_QUEEN;
                case BISHOP -> isWhite ? WHITE_BISHOP : BLACK_BISHOP;
                case KNIGHT -> isWhite ? WHITE_KNIGHT : BLACK_KNIGHT;
                case ROOK -> isWhite ? WHITE_ROOK : BLACK_ROOK;
                case PAWN -> isWhite ? WHITE_PAWN : BLACK_PAWN;
            };
            pieceChar = isWhite ? SET_TEXT_COLOR_WHITE + pieceChar : SET_TEXT_COLOR_BLACK + pieceChar;
        }
        String color = (pos.getRow() + pos.getColumn()) % 2 == 0 ? SET_BG_COLOR_LIGHT_GREY : SET_BG_COLOR_DARK_GREEN;
        printMessage(pieceChar, color);
    }

    private static void printLetterRow(String infoSettings){
        printMessage(EMPTY, infoSettings);
        char col = 'a';
        for (int i = 0; i < 8; i++) {
            printMessage(" " + col + " ", infoSettings);
            col++;
        }
        printMessage(EMPTY + RESET_BG_COLOR + "\n", infoSettings);

    }
}
