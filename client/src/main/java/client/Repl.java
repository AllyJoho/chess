package client;

import server.ServerFacade;
import java.util.Scanner;
import static ui.EscapeSequences.*;
import static client.PrintFunctions.printMessage;

public class Repl {
    private final ChessClient preLoginClient;
    private final ChessClient postLoginClient;
    private final ChessClient gameplayClient;
    private ChessClient client;
    private int state;

    public Repl(String serverUrl) {
        ServerFacade server = new ServerFacade(serverUrl);
        this.preLoginClient = new PreLoginClient(server);
        this.postLoginClient = new PostLoginClient(server);
        this.gameplayClient = new GameplayClient(server);
        this.client = this.preLoginClient;
        this.state = 0;
    }

    public void run(){
        printMessage(ERASE_SCREEN, "");
        printMessage("Welcome to 240 chess. Type help to get started.\n", SET_TEXT_ITALIC);
        Scanner scanner = new Scanner(System.in);

        ClientData data = new ClientData();
        String authToken = "";
        int gameId = -1;
        String username = "";
        while (state != 3){
            EvalResponse response = getInput(scanner, authToken, gameId, username);
            if(response == null){
                continue;
            }
            authToken = response.authToken();
            gameId = response.gameId();
            username = response.authToken();
        }
    }

    private EvalResponse getInput(Scanner scanner, String authToken, int gameId, String username){
        String stage = switch (state) {
            case 0 -> "[LOGGED OUT]";
            case 1 -> "[LOGGED IN]";
            case 2 -> "[IN GAME]";
            default -> null;
        };
        printMessage(stage + ">>> ", "");
        String line = scanner.nextLine();
        try {
            EvalRequest request = new EvalRequest(line, authToken, gameId, username);
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
