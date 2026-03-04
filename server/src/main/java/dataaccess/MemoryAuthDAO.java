package dataaccess;

import model.*;

import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO extends AuthDAO {
    final private HashMap<String, AuthData> sessions = new HashMap<>();
    public AuthData createAuth(String username) throws DataAccessException{
        String authToken = generateToken();
        AuthData session = new AuthData(authToken, username);
        sessions.put(authToken, session);
        return session;
    }
    public AuthData getSession(String token) throws DataAccessException{
        AuthData session = sessions.get(token);
        if (session == null) {
            throw new DataAccessException("unauthorized");
        }
        return session;
    }
    public void deleteSession(String token) throws DataAccessException{
        if(!sessions.containsKey(token)){
            throw new DataAccessException("unauthorized");
        }
        sessions.remove(token);
    }
    public void clear() throws DataAccessException{
        sessions.clear();
    }
    private static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
