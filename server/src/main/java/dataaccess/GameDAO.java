package dataaccess;

import model.GameData;

import java.util.List;

public class GameDAO {
    public GameData createGame(String gameName) throws DataAccessException{return null;}
    public GameData getGame(Integer id) throws DataAccessException{return null;}
    public void updateGame(GameData gameData) throws DataAccessException{}
    public List<GameData> listGames() throws DataAccessException{return null;}
    public void clear() throws DataAccessException{}
}
