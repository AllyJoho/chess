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

    private EvalResponse move(String[] params) throws Exception {
        String message = "";
        if(params.length == 2){
            ChessPosition startPos = getPosFromString(params[0]);
            ChessPosition endPos = getPosFromString(params[1]);
            Collection<ChessMove> moves =  data.getGameData().getGame().validMoves(startPos);
            Collection<ChessMove> filteredMoves = moves.stream()
                    .filter(e -> e.getStartPosition().equals(startPos))
                    .filter(e -> e.getEndPosition().equals(endPos))
                    .toList();
            if(filteredMoves.isEmpty()){
                message = "This isn't a valid move!";
            } else if (filteredMoves.size() == 1) {
//                Add make move logic in a bit
                message = "Move Successful!";
            } else {
                message = "Your pawn can be promoted! What piece should it be promoted to?";
//                Add that logic
            }
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
        GameData gameData = data.getGameData();
        if(data.getGamePerspective() == 1){
            JoinGameRequest request = new JoinGameRequest("WHITE", gameData.getGameID(), data.getUsername());
            server.joinGame(request, data.getAuthToken());
        } else if (data.getGamePerspective() == 2) {
            JoinGameRequest request = new JoinGameRequest("BLACK", gameData.getGameID(), data.getUsername());
            server.joinGame(request, data.getAuthToken());
        }
        data.setGamePerspective(0);
        data.setGameData(null);
        return new EvalResponse("", 1, data);
    }

    private EvalResponse resign() throws Exception {
        GameData gameData = data.getGameData();
        gameData.getGame().setGameOver(true);
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
        int col = letterPart.charAt(0) - 'a';
        int row = Integer.parseInt(digitPart) - 1;
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
