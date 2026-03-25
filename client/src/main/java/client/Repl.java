package client;

import server.ServerFacade;

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
        this.preLoginClient = new PreLoginClient();
        this.postLoginClient = new PreLoginClient();
        this.gameplayClient = new GameplayClient();
        this.client = this.preLoginClient;
        this.state = 0;
    }

    public void run(){
        printMessage("Welcome to 240 chess. Type help to get started.", SET_TEXT_FAINT + SET_TEXT_ITALIC);
//        printMessage(" K ", SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_BLACK + SET_TEXT_BOLD);
//        printMessage("   ", SET_BG_COLOR_DARK_GREEN + SET_TEXT_COLOR_BLACK + SET_TEXT_BOLD);
//        printMessage(" K ", SET_BG_COLOR_LIGHT_GREY + SET_TEXT_COLOR_WHITE);
//        printMessage(BLACK_KNIGHT, SET_BG_COLOR_DARK_GREEN + SET_TEXT_COLOR_WHITE);
        printBoard();
    }
}
//asks for user input and manages state
//passes input to client
//get auth token back