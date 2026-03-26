package client;

import request.LoginRequest;
import request.RegisterRequest;
import result.ListGameResult;
import result.LoginResult;
import result.RegisterResult;
import server.ServerFacade;

import java.util.Arrays;

public class PostLoginClient extends ChessClient {
    public PostLoginClient(ServerFacade server) {
        super(server);
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
                default -> help(request);
            };
        } catch (Exception ex) {
            return new EvalResponse(ex.getMessage(), 0, request.authToken(), request.gameId());
        }
    }

    private EvalResponse create(EvalRequest request, String[] params) throws Exception {
        String message =  "You created an account.";
        RegisterRequest request1 = new RegisterRequest(params[0], params[1], params[2]);
        RegisterResult result = server.register(request1);
        return new EvalResponse(message, 1, result.authToken(), request.gameId());
    }

    private EvalResponse list(EvalRequest request) throws Exception {
        ListGameResult result = server.listGames();
        return new EvalResponse(result.toString(), 1, request.authToken(), request.gameId());
    }

    private EvalResponse join(EvalRequest request, String[] params) throws Exception {
        String message =  "You've successfully logged in " + params[0];
        LoginRequest request1 = new LoginRequest(params[0], params[1]);
        LoginResult result = server.login(request1);
        return new EvalResponse(message, 1, result.authToken(), request.gameId());
    }

    private EvalResponse observe(EvalRequest request, String[] params) throws Exception {
        String message =  "You've successfully logged in " + params[0];
        LoginRequest request1 = new LoginRequest(params[0], params[1]);
        LoginResult result = server.login(request1);
        return new EvalResponse(message, 2, result.authToken(), request.gameId());
    }

    private EvalResponse logout(){
        String message =  "Goodbye!";
        return new EvalResponse(message, 0, "", -1);
    }

    private EvalResponse quit(){
        String message =  "Goodbye!";
        return new EvalResponse(message, 3, "", -1);
    }

    private EvalResponse help(EvalRequest request){
        String message = """
                help - get commands\s
                quit - say goodbye\s
                register <USERNAME> \
                <PASSWORD> <EMAIL> - make an account
                login <USERNAME> <PASSWORD> - play chess""";
        return new EvalResponse(message, 0, request.authToken(), request.gameId());
    }
}
