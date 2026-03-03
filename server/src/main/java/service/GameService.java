package service;

import dataaccess.*;
import model.*;
import request.*;
import result.*;

public class GameService {
    private final GameDAO gameDataAccess;

    public GameService(GameDAO gameDataAccess) {
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
    public void joinGame(JoinGameRequest request) throws DataAccessException {
        if (request.gameID() == 0 || request.username() == null || request.playerColor() == null) {
            throw new DataAccessException("bad request");
        }
        GameData game = gameDataAccess.getGame(request.gameID());
        String whiteName = game.getWhiteUsername();
        String blackName = game.getBlackUsername();
        if(request.playerColor().equals("WHITE")){
            if (whiteName == null){
                whiteName = request.username();
            }else {
                throw new DataAccessException("already taken");
            }
        } else if (request.playerColor().equals("BLACK")) {
            if (blackName == null){
                blackName = request.username();
            }else {
                throw new DataAccessException("already taken");
            }
        }else{
            throw new DataAccessException("bad request");
        }
        GameData newGame = new GameData(request.gameID(), whiteName, blackName, game.getGameName(), game.getGame());
        gameDataAccess.updateGame(newGame);
    }
}
