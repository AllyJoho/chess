package client;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPosition;
import client.websocket.WebSocketFacade;
import model.GameData;
import server.ServerFacade;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static client.PrintFunctions.printBoard;

public class GameplayClient extends ChessClient {
    WebSocketFacade ws;
    public GameplayClient(ServerFacade server, WebSocketFacade ws) {
        super(server);
        this.ws = ws;
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

    private EvalResponse move(String[] params) throws Exception {
        String message = "";
        if(params.length == 2){
            ChessPosition startPos = getPosFromString(params[0]);
            ChessPosition endPos = getPosFromString(params[1]);
            ChessMove move = new ChessMove(startPos, endPos, null);
            ws.makeMove(data, move);
            message = "Move Successful!";
        }else{
            throw new Exception("Incorrect arguments. Please format your move request like this: \n" +
                    "move <START POS> <END POS>");
        }
        return new EvalResponse(message, 2, data);
    }

    private EvalResponse displayBoard() {
        printBoard(data.getGameData().getGame().getBoard(), data.getGamePerspective(), new ArrayList<>(), null);
        return new EvalResponse("", 2, data);
    }

    private EvalResponse highlightMoves(String[] params) throws Exception {
        if(params.length == 1){
            ChessPosition startPos = getPosFromString(params[0]);
            Collection<ChessMove> moves =  data.getGameData().getGame().validMoves(startPos);
            printBoard(data.getGameData().getGame().getBoard(), data.getGamePerspective(), moves, startPos);
        }else{
            throw new Exception("Incorrect arguments. Please format your highlight request like this: \n" +
                    "highlight <POSITION>");
        }
        return new EvalResponse("", 2, data);
    }

    private EvalResponse leaveGame() throws Exception {
        String message =  "You left the game";
        ws.leaveGame(data);
        data.setGamePerspective(0);
        data.setGameData(null);
        return new EvalResponse(message, 1, data);
    }

    private EvalResponse resign() throws Exception {
        if(data.getGamePerspective() == 3){
            throw new Exception("You're observing and can't play the game");
        }
        GameData gameData = data.getGameData();
        gameData.getGame().setGameState(1);
        data.setGamePerspective(0);
        data.setGameData(null);
        return new EvalResponse("", 2, data);
    }

    private ChessPosition getPosFromString(String pos) throws Exception {
        if (pos == null || pos.length() < 2) {
            throw new Exception("Please format the position as column then row with no space");
        }
        String clean = pos.toLowerCase().trim();
        String letterPart = clean.replaceAll("[^a-z]", "");
        String digitPart = clean.replaceAll("[^0-9]", "");
        if (letterPart.isEmpty() || digitPart.isEmpty()) {
            throw new Exception("Position missing row or column.");
        }
        int col = letterPart.charAt(0) - 'a' + 1;
        int row = Integer.parseInt(digitPart);
        return new ChessPosition(row, col);
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
