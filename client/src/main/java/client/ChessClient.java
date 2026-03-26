package client;

import server.ServerFacade;

import java.util.Arrays;

public class ChessClient {
    protected final ServerFacade server;
    public ChessClient(ServerFacade server){
        this.server = server;
    }

    public EvalResponse eval(EvalRequest request){
        return new EvalResponse("", 0, request.authToken(), request.gameId());
    }

    protected EvalResponse secret(EvalRequest request, String[] params) throws Exception {
        String message = "";
        if (params[0].equals("clear")){
            server.clear();
            message = "clear";
        }else if (params[0].equals("auth")) {
            message = request.authToken();
        }
        return new EvalResponse(message, 0, request.authToken(), request.gameId());
    }
}
