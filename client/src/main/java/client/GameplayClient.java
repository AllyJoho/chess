package client;

import chess.ChessMove;
import chess.ChessPosition;
import model.GameData;
import request.JoinGameRequest;
import server.ServerFacade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static client.PrintFunctions.printBoard;

public class GameplayClient extends ChessClient {
    public GameplayClient(ServerFacade server) {
        super(server);
    }

    public EvalResponse eval(EvalRequest request){
        data = request.data();
        try {
            String[] tokens = request.command().toLowerCase().split(" ");
            String cmd = (tokens.length > 0) ? tokens[0] : "help";
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "move" -> move(params);
                case "redraw" -> displayBoard();
                case "highlight" -> highlightMoves(params);
                case "leave" -> leaveGame();
                case "logout" -> resign();
                case "secret" -> secret(request, params, 2);
                default -> help();
            };
        } catch (Exception ex) {
            return new EvalResponse(ex.getMessage(), 2, data);
        }
    }

    private EvalResponse move(String[] params) {
        Collection<ChessMove> moves =  data.getGameData().getGame().validMoves(getPosFromString(params[0]));
        printBoard(data.getGameData().getGame().getBoard(), data.getGamePerspective(), moves, null);
        return new EvalResponse("", 2, data);
    }

    private EvalResponse displayBoard() {
        printBoard(data.getGameData().getGame().getBoard(), data.getGamePerspective(), new ArrayList<>(), null);
        return new EvalResponse("", 2, data);
    }

    private EvalResponse highlightMoves(String[] params) {
        ChessPosition startPos = getPosFromString(params[0]);
        Collection<ChessMove> moves =  data.getGameData().getGame().validMoves(startPos);
        printBoard(data.getGameData().getGame().getBoard(), data.getGamePerspective(), moves, startPos);
        return new EvalResponse("", 2, data);
    }

    private EvalResponse leaveGame() throws Exception {
        data.setGamePerspective(0);
        data.setGameData(null);
        return new EvalResponse("", 1, data);
    }

    private EvalResponse resign() throws Exception {
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
        return new EvalResponse("", 2, data);
    }

    private ChessPosition getPosFromString(String pos){
        return new ChessPosition(2, 1);
    }

    private EvalResponse help(){
        String message = """
                move <START POSITION> <END POSITION> - move the piece in the start position
                to the end position if possible
                redraw - redraw the board
                highlight <PIECE POSITION> - Draws the board with the possible moves on the piece in the position
                leave - exit game
                resign - give up game
                help - get commands""";
        return new EvalResponse(message, 2, data);
    }
}
