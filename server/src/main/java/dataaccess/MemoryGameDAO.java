package dataaccess;

import chess.ChessGame;
import model.*;

import java.util.Collection;
import java.util.HashMap;

public class MemoryGameDAO extends GameDAO {
    private int gameID = 1;
    final private HashMap<Integer, GameData> games = new HashMap<>();
    public GameData createGame(String gameName) throws DataAccessException{
        ChessGame chessGame = new ChessGame();
        GameData game = new GameData(gameID++, null, null, gameName, chessGame);
        games.put(gameID, game);
        return game;
    }
    public GameData getGame(Integer id) throws DataAccessException{return null;}
    public GameData updateGame(GameData gameData) throws DataAccessException{return null;}
    public Collection<GameData> listGames() throws DataAccessException{
        return games.values();
    }
    public void clear() throws DataAccessException{
        gameID = 1;
        games.clear();
    }
}
