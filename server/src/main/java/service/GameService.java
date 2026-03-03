package service;

import dataaccess.*;
import model.*;
import request.*;
import result.*;

public class GameService {
    private final UserDAO userDataAccess;
    private final GameDAO gameDataAccess;

    public GameService(UserDAO userDataAccess, GameDAO gameDataAccess) {
        this.userDataAccess = userDataAccess;
        this.gameDataAccess = gameDataAccess;
    }
    public ListGameResult listGames() throws DataAccessException {
        return new ListGameResult(gameDataAccess.listGames());
    }
    public CreateGameResult createGame(CreateGameRequest request) throws DataAccessException {
        if (request.gameName() == null) {
            throw new DataAccessException("bad request");
        }
        GameData game = gameDataAccess.createGame(request.gameName());
        return new CreateGameResult(game.getGameID());
    }
    public void joinGame(JoinGameRequest request){
        return;
    }
}
