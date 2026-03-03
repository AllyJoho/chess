package service;
import dataaccess.*;
import model.*;
        import request.*;
        import result.*;

public class AuthService {
    private final UserDAO userDataAccess;
    private final AuthDAO authDataAccess;
    private final GameDAO gameDataAccess;

    public AuthService(UserDAO userDataAccess, AuthDAO authDataAccess, GameDAO gameDataAccess) {
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
        this.gameDataAccess = gameDataAccess;
    }
    public void clear() throws DataAccessException {
        userDataAccess.clear();
        authDataAccess.clear();
        gameDataAccess.clear();
    }
}
