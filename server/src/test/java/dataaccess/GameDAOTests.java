package dataaccess;

import model.AuthData;
import model.GameData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GameDAOTests {
    MySqlGameDAO dao = new MySqlGameDAO();

    @Test
    public void createGamePositive() throws DataAccessException {
        dao.clear();
        Assertions.assertDoesNotThrow(() -> dao.createGame("gameName1"));
    }

    @Test
    public void createGameNegative() throws DataAccessException {
        dao.clear();
        dao.createGame("gameName1");
        Assertions.assertDoesNotThrow(() -> dao.createGame("gameName1"));
    }

    @Test
    public void getGamePositive() throws DataAccessException {
        dao.clear();
        GameData game1 = dao.createGame("gameName1");
        dao.createGame("gameName2");
        Assertions.assertDoesNotThrow(() -> dao.getGame(game1.getGameID()));
    }

    @Test
    public void getGameNegative() throws DataAccessException {
        dao.clear();
        Assertions.assertThrows(DataAccessException.class, () -> dao.getGame(-1));
    }

    @Test
    public void updateGamePositive() throws DataAccessException {
        dao.clear();
        GameData game1 = dao.createGame("gameName1");
        GameData game2 = dao.createGame("gameName2");
        GameData newGame = new GameData(game1.getGameID(), null, "hey", game1.getGameName(), game1.getGame());

//        AuthData auth = dao.createAuth("user1");
//        Assertions.assertDoesNotThrow(() -> dao.deleteSession(auth.getAuthToken()));
    }

    @Test
    public void updateGameNegative() throws DataAccessException {
        dao.clear();
//        dao.createAuth("user1");
//        dao.createAuth("user2");
//        dao.createAuth("user3");
//        Assertions.assertDoesNotThrow(() -> dao.deleteSession(null));
//        try (Connection conn = DatabaseManager.getConnection()) {
//            try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT COUNT(*) FROM sessions")) {
//                ResultSet rs = preparedStatement.executeQuery();
//                rs.next();
//                Assertions.assertEquals(3, rs.getInt(1));
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
    }

    @Test
    public void listGamesPositive() throws DataAccessException {
        dao.clear();
//        AuthData auth = dao.createAuth("user1");
//        Assertions.assertDoesNotThrow(() -> dao.deleteSession(auth.getAuthToken()));
    }

    @Test
    public void listGamesNegative() throws DataAccessException {
        dao.clear();
    }
    @Test
    public void clear() throws DataAccessException {
        Assertions.assertDoesNotThrow(() -> dao.clear());
    }
}
