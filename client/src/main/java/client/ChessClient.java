package client;

import server.ServerFacade;

import java.util.Arrays;

public class ChessClient {
    protected final ServerFacade server;
    protected ClientData data;

    public ChessClient(ServerFacade server){
        this.server = server;
    }

    public EvalResponse eval(EvalRequest request){
        return new EvalResponse("", 0, request.data());
    }

    protected EvalResponse secret(EvalRequest request, String[] params, int status) throws Exception {
        String message = "";
        if (params[0].equals("clear")){
            server.clear();
            message = "clear";
        }else if (params[0].equals("auth")) {
            message = request.data().getAuthToken();
        } else if (params[0].equals("name")) {
            message = request.data().getUsername();
        }
        return new EvalResponse(message, status, request.data());
    }
}
