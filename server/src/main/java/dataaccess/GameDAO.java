package dataaccess;

import model.*;

import java.util.HashMap;

public class GameDAO {
    final private HashMap<Integer, GameData> games = new HashMap<>();
    public void clear() throws DataAccessException{
        games.clear();
    }
}
