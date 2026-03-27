package client;

import model.GameData;
import request.CreateGameRequest;
import request.JoinGameRequest;
import request.LoginRequest;
import request.RegisterRequest;
import result.CreateGameResult;
import result.ListGameResult;
import result.LoginResult;
import result.RegisterResult;
import server.ServerFacade;

import java.util.ArrayList;
import java.util.Arrays;

public class PostLoginClient extends ChessClient {
    ArrayList<GameData> gamesLink;
    public PostLoginClient(ServerFacade server) {
        super(server);
        gamesLink = new ArrayList<GameData>();
    }

    public EvalResponse eval(EvalRequest request){
        try {
            String[] tokens = request.line().toLowerCase().split(" ");
            String cmd = (tokens.length > 0) ? tokens[0] : "help";
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "create" -> create(request, params);
                case "list" -> list(request);
                case "join" -> join(request, params);
                case "observe" -> observe(request, params);
                case "logout" -> logout();
                case "quit" -> quit();
                case "secret" -> secret(request, params, 1);
                default -> help(request);
            };
        } catch (Exception ex) {
            return new EvalResponse(ex.getMessage(), 0, request.authToken(), request.gameId(), request.user());
        }
    }

    private EvalResponse create(EvalRequest request, String[] params) throws Exception {
        String message =  "You've successfully created a game called " + params[0];
        CreateGameResult result;
        if(params.length == 1){
            CreateGameRequest request1 = new CreateGameRequest(params[0]);
            result = server.createGame(request1, request.authToken());
        }else{
            throw new Exception("Incorrect arguments. Please format your create game request like this: \n" +
                    "create <NAME>");
        }
        return new EvalResponse(message, 1, request.authToken(), request.gameId(), request.user());
    }

    private EvalResponse list(EvalRequest request) throws Exception {
        ListGameResult result = server.listGames(request.authToken());
        StringBuilder resultString = new StringBuilder();
        for (int i = 0; i < result.games().size(); i++) {
            GameData gameData = result.games().get(i);
            String gameString = (i+1) + " - " + gameData.getGameName() + " WHITE: " +
                    gameData.getWhiteUsername() + "BLACK: " + gameData.getBlackUsername();
            gamesLink.add(gameData);
            resultString.append(gameString);
        }
        return new EvalResponse(resultString.toString(), 1, request.authToken(), request.gameId(), request.user());
    }

    private EvalResponse join(EvalRequest request, String[] params) throws Exception {
        String message =  "You've successfully joined game " + params[0];
        if(params.length == 2){
            JoinGameRequest request1 = new JoinGameRequest(params[0], Integer.parseInt(params[1]), request.user());
            server.joinGame(request1, request.authToken());
        }else{
            throw new Exception("Incorrect arguments. Please format your join request like this: \n" +
                    "join <ID> [WHITE | BLACK]");
        }
        return new EvalResponse(message, 2, request.authToken(), request.gameId(), request.user());
    }

    private EvalResponse observe(EvalRequest request, String[] params) throws Exception {
        String message =  "You're observing game " + params[0];
        if(params.length == 1){
            GameData gameData = gamesLink.get(Integer.parseInt(params[0]) - 1);
            return new EvalResponse(message, 2, request.authToken(), request.gameId(), request.user());
        }else{
            throw new Exception("Incorrect arguments. Please format your create game request like this: \n" +
                    "create <NAME>");
        }
    }

    private EvalResponse logout(){
        String message =  "Goodbye!";
        return new EvalResponse(message, 0, "", -1, "");
    }

    private EvalResponse quit(){
        String message =  "Goodbye!";
        return new EvalResponse(message, 3, "", -1, "");
    }

    private EvalResponse help(EvalRequest request){
        String message = """
                create <NAME> - make a new game
                list - list all the games
                join <ID> [WHITE | BLACK] - join a game and specify the color
                observe <ID> - watch a game
                logout - when you are done
                quit - say goodbye
                help - get commands""";
        return new EvalResponse(message, 1, request.authToken(), request.gameId(), request.user());
    }
}
