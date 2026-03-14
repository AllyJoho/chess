package dataaccess;

import chess.ChessGame;
import com.google.gson.Gson;
import model.GameData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class MySqlGameDAO  extends GameDAO {
    private final Gson serializer;

    public MySqlGameDAO() {
        this.serializer = new Gson();
        try {
            configureDatabase();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    public GameData createGame(String gameName) throws DataAccessException{
        ChessGame chessGame = new ChessGame();
        String statement = "INSERT INTO games (game_name, white_username, black_username, game_json) \n" +
                "VALUES (?, \"\", \"\", ?)";
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(statement, RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, gameName);
                preparedStatement.setString(2, serializer.toJson(chessGame));
                preparedStatement.executeUpdate();
                ResultSet rs = preparedStatement.getGeneratedKeys();
                rs.next();
                GameData game = new GameData(rs.getInt(1), null, null, gameName, chessGame);
                return game;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public GameData getGame(Integer id) throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = conn.prepareStatement("SELECT * FROM games WHERE game_id = ?")) {
                preparedStatement.setInt(1, id);
                ResultSet rs = preparedStatement.executeQuery();
                if(rs.next()){
                    int gameIdResult = rs.getInt("game_id");
                    String gameNameResult = rs.getString("game_name");
                    String whiteResult = rs.getString("white_username");
                    String white = (whiteResult == null || whiteResult.isEmpty()) ? null : whiteResult;
                    String blackResult = rs.getString("black_username");
                    String black = (blackResult == null || blackResult.isEmpty()) ? null : blackResult;
                    String jsonResult = rs.getString("game_json");
                    ChessGame game = serializer.fromJson(jsonResult, ChessGame.class);
                    return new GameData(gameIdResult, white, black, gameNameResult, game);
                }
                return null;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void updateGame(GameData gameData) throws DataAccessException{
        String statement = "UPDATE games SET white_username = ?, black_username = ?, game_json = ? WHERE game_id = ?";
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(statement)) {
                String white = (gameData.getWhiteUsername() == null) ? "" : gameData.getWhiteUsername();
                preparedStatement.setString(1, white);
                String black = (gameData.getBlackUsername() == null) ? "" : gameData.getBlackUsername();
                preparedStatement.setString(2, black);
                preparedStatement.setString(3, serializer.toJson(gameData.getGame()));
                preparedStatement.setInt(4, gameData.getGameID());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public List<GameData> listGames() throws DataAccessException{
        var games = new ArrayList<GameData>();
        String statement = "SELECT game_id FROM games";
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = conn.prepareStatement(statement)) {
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("game_id");
                    games.add(getGame(id));
                }
                return games;
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void clear() throws DataAccessException{
        try (Connection conn = DatabaseManager.getConnection()) {
            try (PreparedStatement preparedStatement = conn.prepareStatement("TRUNCATE games")) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private static String generateToken() {
        return UUID.randomUUID().toString();
    }

    private final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  games (
              game_id int NOT NULL AUTO_INCREMENT,
              game_name varchar(256) NOT NULL,
              white_username varchar(256),
              black_username varchar(256),
              game_json TEXT,
              PRIMARY KEY (game_id)
            ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci
            """
    };

    private void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (Connection conn = DatabaseManager.getConnection()) {
            for (String statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }
}