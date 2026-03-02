package dataaccess;

import model.*;

import java.util.HashMap;
import java.util.UUID;

public class MemoryAuthDAO extends AuthDAO {
    final private HashMap<String, AuthData> sessions = new HashMap<>();
    AuthData createAuth(AuthData a) throws DataAccessException{
        AuthData session = new AuthData(a.getUsername(), generateToken());
        sessions.put(a.getAuthToken(), session);
        return session;
    }
    AuthData getSession(String token) throws DataAccessException{
        return sessions.get(token);
    }
    void deleteSession(String token) throws DataAccessException{
        sessions.remove(token);
    }
    void clear() throws DataAccessException{
        sessions.clear();
    }
    public static String generateToken() {
        return UUID.randomUUID().toString();
    }
}
