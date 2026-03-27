package client;

public class ClientData {
    private String username;
    private String authToken;
    private int gameId;
    private int gamePerspective;
    public ClientData(){
        username = "";
        authToken = "";
        gameId = -1;
        gamePerspective = 0;
    }

    public int getGamePerspective() {
        return gamePerspective;
    }

    public void setGamePerspective(int gamePerspective) {
        this.gamePerspective = gamePerspective;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
