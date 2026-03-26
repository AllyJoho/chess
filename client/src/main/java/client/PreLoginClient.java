package client;

import request.LoginRequest;
import request.RegisterRequest;
import result.LoginResult;
import result.RegisterResult;
import server.ServerFacade;

import java.util.Arrays;

public class PreLoginClient extends ChessClient {
    public PreLoginClient(ServerFacade server) {
        super(server);
    }

    public EvalResponse eval(EvalRequest request){
        try {
            String[] tokens = request.line().toLowerCase().split(" ");
            String cmd = (tokens.length > 0) ? tokens[0] : "help";
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "register" -> register(request, params);
                case "login" -> login(request, params);
                case "quit" -> quit(request);
                default -> help(request);
            };
        } catch (Exception ex) {
            return new EvalResponse(ex.getMessage(), 0, request.authToken(), request.gameId());
        }
    }

    private EvalResponse register(EvalRequest request, String[] params) throws Exception {
        server.clear();
        String message =  "You created an account.";
        RegisterResult result;
        if(params.length == 3){
            RegisterRequest request1 = new RegisterRequest(params[0], params[1], params[2]);
            result = server.register(request1);
        }else{
            throw new Exception("Incorrect arguments. Please format your register request like this: \n" +
                    "register <USERNAME> <PASSWORD> <EMAIL>");
        }
        return new EvalResponse(message, 1, result.authToken(), request.gameId());
    }

    private EvalResponse login(EvalRequest request, String[] params) throws Exception {
        String message =  "You've successfully logged in " + params[0];
        LoginResult result;
        if(params.length == 2){
            LoginRequest request1 = new LoginRequest(params[0], params[1]);
            result = server.login(request1);
        }else{
            throw new Exception("Incorrect arguments. Please format your login request like this: \n" +
                    "login <USERNAME> <PASSWORD>");
        }
        return new EvalResponse(message, 1, result.authToken(), request.gameId());
    }

    private EvalResponse quit(EvalRequest request){
        String message =  "Goodbye!";
        return new EvalResponse(message, 3, request.authToken(), request.gameId());
    }

    private EvalResponse help(EvalRequest request){
        String message = """
                help - get commands
                quit - say goodbye
                register <USERNAME> <PASSWORD> <EMAIL> - make an account
                login <USERNAME> <PASSWORD> - play chess""";
        return new EvalResponse(message, 0, request.authToken(), request.gameId());
    }
}