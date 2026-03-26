package client;

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
//                case "register" -> signIn(params);
//                case "login" -> rescuePet(params);
                case "quit" -> quit(request);
                default -> help(request);
            };
        } catch (Exception ex) {
            return new EvalResponse(ex.getMessage(), 0, request.authToken(), request.gameId());
        }
    }

    private EvalResponse register(EvalRequest request, String[] params){
        String message =  "Goodbye!";
//        server.register;
        return new EvalResponse(message, 3, request.authToken(), request.gameId());
    }

    private EvalResponse login(EvalRequest request, String[] params){
        String message =  "Goodbye!";
        return new EvalResponse(message, 3, request.authToken(), request.gameId());
    }

    private EvalResponse quit(EvalRequest request){
        String message =  "Goodbye!";
        return new EvalResponse(message, 3, request.authToken(), request.gameId());
    }

    private EvalResponse help(EvalRequest request){
        String message =  "Do you get it?";
        return new EvalResponse(message, 0, request.authToken(), request.gameId());
    }
}
//uses username and password to pass to the server facade
// gets response and passes authtoken