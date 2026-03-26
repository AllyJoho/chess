package client;

import server.ServerFacade;

import java.util.Arrays;

public class ChessClient {
    private final ServerFacade server;
    public ChessClient(ServerFacade server){
        this.server = server;
    }
    public EvalResponse eval(EvalRequest request){
        return new EvalResponse("", 0, request.authToken(), request.gameId());
    }
}
