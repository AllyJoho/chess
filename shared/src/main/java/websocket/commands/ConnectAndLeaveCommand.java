package websocket.commands;

public class ConnectAndLeaveCommand extends UserGameCommand {
    private final String username;

    public ConnectAndLeaveCommand(UserGameCommand.CommandType commandType,
                          String authToken,
                          Integer gameID,
                          String username) {
        super(commandType, authToken, gameID);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
