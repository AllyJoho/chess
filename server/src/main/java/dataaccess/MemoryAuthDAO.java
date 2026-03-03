package dataaccess;

import model.*;

import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO extends AuthDAO {
    final private HashMap<String, AuthData> sessions = new HashMap<>();
    public AuthData createAuth(String username) throws DataAccessException{
        String authToken = generateToken();
        AuthData session = new AuthData(username, authToken);
        sessions.put(authToken, session);
        return session;
    }
    public AuthData getSession(String token) throws DataAccessException{
        return sessions.get(token);
    }
    public void deleteSession(String token) throws DataAccessException{
        sessions.remove(token);
    }
    public void clear() throws DataAccessException{
        sessions.clear();
    }
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
