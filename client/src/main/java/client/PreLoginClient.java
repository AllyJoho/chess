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
        data = request.data();
        try {
            String[] tokens = request.command().toLowerCase().split(" ");
            String cmd = (tokens.length > 0) ? tokens[0] : "help";
            String[] params = Arrays.copyOfRange(tokens, 1, tokens.length);
            return switch (cmd) {
                case "register" -> register(params);
                case "login" -> login(params);
                case "quit" -> quit();
                case "secret" -> secret(request, params, 0);
                default -> help();
            };
        } catch (Exception ex) {
            return new EvalResponse(ex.getMessage(), 0, request.data());
        }
    }

    private EvalResponse register(String[] params) throws Exception {
        String message =  "You created an account.";
        RegisterResult result;
        if(params.length == 3){
            RegisterRequest request = new RegisterRequest(params[0], params[1], params[2]);
            result = server.register(request);
        }else{
            throw new Exception("Incorrect arguments. Please format your register request like this: \n" +
                    "register <USERNAME> <PASSWORD> <EMAIL>");
        }
        data.setUsername(result.username());
        data.setAuthToken(result.authToken());
        return new EvalResponse(message, 1, data);
    }

    private EvalResponse login(String[] params) throws Exception {
        String message =  "You've successfully logged in " + params[0];
        LoginResult result;
        if(params.length == 2){
            LoginRequest request = new LoginRequest(params[0], params[1]);
            result = server.login(request);
        }else{
            throw new Exception("Incorrect arguments. Please format your login request like this: \n" +
                    "login <USERNAME> <PASSWORD>");
        }
        data.setUsername(result.username());
        data.setAuthToken(result.authToken());
        return new EvalResponse(message, 1, data);
    }

    private EvalResponse quit(){
        String message =  "Goodbye!";
        return new EvalResponse(message, 3, data);
    }

    private EvalResponse help(){
        String message = """
                register <USERNAME> <PASSWORD> <EMAIL> - make an account
                login <USERNAME> <PASSWORD> - play chess
                quit - say goodbye
                help - get commands""";
        return new EvalResponse(message, 0, data);
    }
}