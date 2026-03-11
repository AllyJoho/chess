package service;
import dataaccess.*;

public class AuthService {
    private final AuthDAO authDataAccess;

    public AuthService(AuthDAO authDataAccess) {
        this.authDataAccess = authDataAccess;
    }

    public boolean authorize(String authToken){
        try {
            authDataAccess.getSession(authToken);
            return true;
        } catch (DataAccessException e) {
            return false;
        }
    }

    public String getUser(String authToken) throws DataAccessException {
        return authDataAccess.getSession(authToken).getUsername();
    }
}
