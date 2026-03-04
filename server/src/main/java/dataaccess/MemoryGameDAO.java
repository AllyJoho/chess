package dataaccess;

import chess.ChessGame;
import model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MemoryGameDAO extends GameDAO {
    private int gameID = 1;
    final private HashMap<Integer, GameData> games = new HashMap<>();
    public GameData createGame(String gameName) throws DataAccessException{
        ChessGame chessGame = new ChessGame();
        GameData game = new GameData(gameID, null, null, gameName, chessGame);
        games.put(gameID, game);
        gameID++;
        return game;
    }
    public GameData getGame(Integer id) throws DataAccessException{
        if(!games.containsKey(id)){
            throw new DataAccessException("bad request");
        }
        return games.get(id);
    }
    public void updateGame(GameData gameData) throws DataAccessException{
        games.replace(gameData.getGameID(), gameData);
    }
    public List<GameData> listGames() throws DataAccessException{
        return new ArrayList<>(games.values());
    }
    public void clear() throws DataAccessException{
        gameID = 1;
        games.clear();
    }
}
