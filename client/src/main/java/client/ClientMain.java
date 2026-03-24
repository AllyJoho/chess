package client;
import server.ServerFacade;

import chess.*;

public class ClientMain {
    private final ServerFacade server;

    public ClientMain(ServerFacade server) {
        this.server = server;
//        this.client = client;
    }

    public static void main(String[] args) {
        var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
        System.out.println("♕ 240 Chess Client: " + piece);

    }
}
//start repl