package client;
import chess.*;
import chess.move.*;

import java.util.ArrayList;
import java.util.Collection;

import static ui.EscapeSequences.*;

public class PrintFunctions {
    public static void printBoard(ChessBoard board,
                                  int perspective,
                                  Collection<ChessMove> moves,
                                  ChessPosition startPos){
        if(perspective == 1 || perspective == 3){
            printChess(board, "W", moves, startPos);
        }else{
            printChess(board,"B", moves, startPos);
        }
    }

    public static void printMessage(String message, String textSettings){
        String reset = RESET_TEXT_BOLD_FAINT + RESET_TEXT_ITALIC + RESET_TEXT_UNDERLINE
                + RESET_TEXT_BLINKING + RESET_TEXT_COLOR + RESET_BG_COLOR;
        System.out.print(textSettings + message + reset);
    }

    private static void printChess(ChessBoard board,
                                   String color,
                                   Collection<ChessMove> moves,
                                   ChessPosition startPos) {
        String infoSettings = SET_TEXT_COLOR_BLACK + SET_BG_COLOR_WHITE;
        boolean direction = color.equals("W");
        int start = direction ? 7 : 0;
        int stop = direction ? -1 : 8;
        int step = direction ? -1 : 1;

        int startj = direction ? 0 : 7;
        int stopj = direction ? 8 : -1;
        int stepj = direction ? 1 : -1;

        Collection<ChessPosition> highlighted = new ArrayList<>();
        for (ChessMove move : moves){
            highlighted.add(move.getEndPosition());
        }

        printLetterRow(infoSettings, step);
        for (int i = start; i != stop; i += step) {
            printMessage(" " + (i + 1) + " ", infoSettings);
            for (int j = startj; j != stopj; j += stepj) {
                ChessPosition pos = new ChessPosition(i+1,j+1);
                ChessPiece piece = board.getPiece(pos);
                boolean isHighlighted = highlighted.contains(pos);
                boolean isMain = pos.equals(startPos);
                printPiece(piece, pos, isHighlighted, isMain);
            }
            printMessage(" " + (i + 1) + " " + RESET_BG_COLOR + "\n", infoSettings);
        }
        printLetterRow(infoSettings, step);
    }

    private static void printPiece(ChessPiece piece, ChessPosition pos, boolean isHighlighted, boolean isMain){
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
        if(isHighlighted){
            color = (pos.getRow() + pos.getColumn()) % 2 == 0 ? SET_BG_COLOR_BLUE : SET_BG_COLOR_GREEN;
        }
        if(isMain){
            color = SET_BG_COLOR_MAGENTA;
        }
        printMessage(pieceChar, color);
    }

    private static void printLetterRow(String infoSettings, int step){
        printMessage(EMPTY, infoSettings);
        char col = step > 0 ? 'a' : 'h';
        for (int i = 0; i < 8; i++) {
            printMessage(" " + col + " ", infoSettings);
            col += (char) step;
        }
        printMessage(EMPTY + RESET_BG_COLOR + "\n", infoSettings);

    }
}
