package client;

import model.GameData;

public class ClientData {
    private String username;
    private String authToken;
    private GameData gameData;
    private int gamePerspective;
    public ClientData(){
        username = "";
        authToken = "";
        gameData = null;
        gamePerspective = 0;
    }

    public int getGamePerspective() {
        return gamePerspective;
    }

    public void setGamePerspective(int gamePerspective) {
        this.gamePerspective = gamePerspective;
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

    public GameData getGameData() {
        return gameData;
    }

    public void setGameData(GameData gameData) {
        this.gameData = gameData;
    }
}
