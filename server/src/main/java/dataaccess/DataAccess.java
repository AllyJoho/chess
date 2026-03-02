package dataaccess;
import dataaccess.DataAccessException;
import model.*;

public class DataAccess {
    void getUser(UserData u) throws DataAccessException{}
    void createUser(UserData u) throws DataAccessException{}
    void createAuth(UserData u) throws DataAccessException{}
    void getSession(String token) throws DataAccessException{}
    void deleteSession(String token) throws DataAccessException{}
    void createGame(UserData u) throws DataAccessException{}
    void createGameID(UserData u) throws DataAccessException{}
    void getGame(int id) throws DataAccessException{}
    void joinGame(UserData u) throws DataAccessException{}
    void getGameList(GameData d) throws DataAccessException{}
    void updateGame(GameData d) throws DataAccessException{}
    void clear() throws DataAccessException{}
}
//clear: A method for clearing all data from the database. This is used during testing.
//createUser: Create a new user.
//getUser: Retrieve a user with the given username.
//createGame: Create a new game.
//getGame: Retrieve a specified game with the given game ID.
//listGames: Retrieve all games.
//updateGame: Updates a chess game. It should replace the chess game string corresponding to a given gameID. This is used when players join a game or when a move is made.
//createAuth: Create a new authorization.
//getAuth: Retrieve an authorization given an authToken.
//deleteAuth: Delete an authorization so that it is no longer valid.