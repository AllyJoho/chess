package client;

import chess.ChessMove;
import chess.ChessPosition;
import model.GameData;
import request.JoinGameRequest;
import server.ServerFacade;

import java.util.ArrayList;
import java.util.Collection;

import static client.PrintFunctions.printBoard;

public class GameplayClient extends ChessClient {
    public GameplayClient(ServerFacade server) {
        super(server);
    }

    public ClientData displayBoard(ClientData request) {
        data = request;
        printBoard(data.getGameData().getGame().getBoard(), data.getGamePerspective(), new ArrayList<>());
        try {
            leaveGame();
        } catch (Exception e) {
            data.setGamePerspective(0);
            data.setGameData(null);
        }
        return data;
    }

    public ClientData highlightMoves(ClientData request, String[] params) {
        data = request;
        Collection<ChessMove> moves =  data.getGameData().getGame().validMoves(getPosFromString(params[0]));
        printBoard(data.getGameData().getGame().getBoard(), data.getGamePerspective(), moves);
        try {
            leaveGame();
        } catch (Exception e) {
            data.setGamePerspective(0);
            data.setGameData(null);
        }
        return data;
    }

    private void leaveGame() throws Exception {
        GameData gameData = data.getGameData();
        if(data.getGamePerspective() == 1){
            JoinGameRequest request = new JoinGameRequest("WHITE", gameData.getGameID(), "");
            server.joinGame(request, data.getAuthToken());
        } else if (data.getGamePerspective() == 2) {
            JoinGameRequest request = new JoinGameRequest("BLACK", gameData.getGameID(), "");
            server.joinGame(request, data.getAuthToken());
        }
        data.setGamePerspective(0);
        data.setGameData(null);
    }

    private ChessPosition getPosFromString(String pos){
        return new ChessPosition(1, 1);
    }

}
