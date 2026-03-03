package dataaccess;

import model.*;

import java.util.Collection;
import java.util.HashMap;

public class GameDAO {
    public GameData createGame(String gameName) throws DataAccessException{return null;}
    public GameData getGame(Integer id) throws DataAccessException{return null;}
    public void updateGame(GameData gameData) throws DataAccessException{}
    public Collection<GameData> listGames() throws DataAccessException{return null;}
    public void clear() throws DataAccessException{}
}
