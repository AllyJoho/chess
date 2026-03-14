package service;
import dataaccess.*;
import model.AuthData;

public class AuthService {
    private final AuthDAO authDataAccess;

    public AuthService(AuthDAO authDataAccess) {
        this.authDataAccess = authDataAccess;
    }

    public boolean authorize(String authToken) throws DataAccessException {
        AuthData session = authDataAccess.getSession(authToken);
        return session != null;
    }

    public String getUser(String authToken) throws DataAccessException {
        AuthData session = authDataAccess.getSession(authToken);
        if (session == null) {
            throw new DataAccessException("unauthorized");
        }
        return session.getUsername();
    }
}
