package client;

import server.ServerFacade;

import java.util.Scanner;

import static client.PrintFunctions.printBoard;
import static ui.EscapeSequences.*;
//import client.PrintFunctions;

import static client.PrintFunctions.printMessage;

public class Repl {
    private final ServerFacade server;
    private final ChessClient preLoginClient;
    private final ChessClient postLoginClient;
    private final ChessClient gameplayClient;
    private ChessClient client;
    private int state;

    public Repl(String serverUrl) {
        this.server = new ServerFacade(serverUrl);
        this.preLoginClient = new PreLoginClient(this.server);
        this.postLoginClient = new PreLoginClient(this.server);
        this.gameplayClient = new GameplayClient(this.server);
        this.client = this.preLoginClient;
        this.state = 0;
    }

    public void run(){
        printMessage("Welcome to 240 chess. Type help to get started.\n", SET_TEXT_ITALIC);
        Scanner scanner = new Scanner(System.in);
        String authToken = "";
        int gameId = -1;
        while (state != 3){
            EvalResponse response = getInput(scanner, authToken, gameId);
            if(response == null){
                continue;
            }
            authToken = response.authToken();
            gameId = response.gameId();
        }
    }

    private EvalResponse getInput(Scanner scanner, String authToken, int gameId){
        String stage = switch (state) {
            case 0 -> "[LOGGED OUT]";
            case 1 -> "[LOGGED IN]";
            case 2 -> "[IN GAME]";
            default -> null;
        };
        printMessage(stage + ">>>", "");
        String line = scanner.nextLine();
        try {
            EvalRequest request = new EvalRequest(line, authToken, gameId);
            EvalResponse response = client.eval(request);
            printMessage(response.message() + "\n", "");
            setState(response.status());
            return response;
        } catch (Throwable e) {
            String result = e.toString();
            printMessage(result + "\n", "");
        }
        return null;
    }

    private void setState(int state){
        this.state = state;
        this.client = switch (state) {
            case 0 -> this.preLoginClient;
            case 1 -> this.postLoginClient;
            case 2 -> this.gameplayClient;
            default -> null;
        };
    }
}
//asks for user input and manages state
//passes input to client
//get auth token back