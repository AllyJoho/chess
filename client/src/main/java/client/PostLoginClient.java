package client;

import model.GameData;
import request.*;
import result.*;
import server.ServerFacade;

import java.util.ArrayList;
import java.util.Arrays;

public class PostLoginClient extends ChessClient {
    ArrayList<GameData> gamesLink;
    public PostLoginClient(ServerFacade server) {
        super(server);
        gamesLink = new ArrayList<>();
    }

    public EvalResponse eval(EvalRequest request){
        data = request.data();
        try {
            String[] tokens = request.command().toLowerCase().split(" ");
            String cmd = (tokens.length > 0) ? tokens[0] : "help";
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "create" -> create(params);
                case "list" -> list();
                case "join" -> join(params);
                case "observe" -> observe(params);
                case "logout" -> logout();
                case "quit" -> quit();
                case "secret" -> secret(request, params, 1);
                default -> help();
            };
        } catch (Exception ex) {
            return new EvalResponse(ex.getMessage(), 1, data);
        }
    }

    private EvalResponse create(String[] params) throws Exception {
        String message =  "You've successfully created a game called " + params[0];
        if(params.length == 1){
            CreateGameRequest request = new CreateGameRequest(params[0]);
            server.createGame(request, data.getAuthToken());
        }else{
            throw new Exception("Incorrect arguments. Please format your create game request like this: \n" +
                    "create <NAME>");
        }
        return new EvalResponse(message, 1, data);
    }

    private EvalResponse list() throws Exception {
        ListGameResult result = server.listGames(data.getAuthToken());
        StringBuilder resultString = new StringBuilder();
        for (int i = 0; i < result.games().size(); i++) {
            GameData gameData = result.games().get(i);
            String gameString = (i+1) + " - " + gameData.getGameName() + " WHITE: " +
                    gameData.getWhiteUsername() + " BLACK: " + gameData.getBlackUsername() + "\n";
            gamesLink.add(gameData);
            resultString.append(gameString);
        }
        return new EvalResponse(resultString.toString(), 1, data);
    }

    private EvalResponse join(String[] params) throws Exception {
        String message =  "You've successfully joined game " + params[0];
        if(params.length == 2){
            String color = params[1].toUpperCase();
            JoinGameRequest request = new JoinGameRequest(color, intFromStr(params[0]), data.getUsername());
            server.joinGame(request, data.getAuthToken());
            GameData gameData = getGame(intFromStr(params[0]));
            data.setGameData(gameData);
            if(color.equals("WHITE")){
                data.setGamePerspective(1);
            }else{
                data.setGamePerspective(2);
            }
        }else{
            throw new Exception("Incorrect arguments. Please format your join request like this: \n" +
                    "join <ID> [WHITE | BLACK]");
        }
        return new EvalResponse(message, 2, data);
    }

    private EvalResponse observe(String[] params) throws Exception {
        String message =  "You're observing game " + params[0];
        if(params.length == 1){
            GameData gameData = getGame(intFromStr(params[0]));
            data.setGameData(gameData);
            data.setGamePerspective(3);
            return new EvalResponse(message, 2, data);
        }else{
            throw new Exception("Incorrect arguments. Please format your create game request like this: \n" +
                    "create <NAME>");
        }
    }

    private EvalResponse logout() throws Exception {
        String message =  "Goodbye!";
        LogoutRequest request = new LogoutRequest(data.getAuthToken());
        server.logout(request, data.getAuthToken());
        data.setUsername("");
        data.setAuthToken("");
        return new EvalResponse(message, 0, data);
    }

    private EvalResponse quit(){
        String message =  "Goodbye!";
        return new EvalResponse(message, 3, data);
    }

    private EvalResponse help(){
        String message = """
                create <NAME> - make a new game
                list - list all the games
                join <ID> [WHITE | BLACK] - join a game and specify the color
                observe <ID> - watch a game
                logout - when you are done
                quit - say goodbye
                help - get commands""";
        return new EvalResponse(message, 1, data);
    }

    private GameData getGame(int game) throws Exception {
        if (game > gamesLink.size()){
            throw new Exception("We don't know what game you're referencing. List the games and pick one of those.");
        }
        return gamesLink.get(game - 1);
    }
}
