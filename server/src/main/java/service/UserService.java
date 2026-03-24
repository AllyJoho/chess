package service;
import dataaccess.*;
import model.*;

public class UserService {
    private final UserDAO userDataAccess;
    private final AuthDAO authDataAccess;
    private final GameDAO gameDataAccess;

    public UserService(UserDAO userDataAccess, AuthDAO authDataAccess, GameDAO gameDataAccess) {
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
        this.gameDataAccess = gameDataAccess;;
    }
    public RegisterResult register(RegisterRequest registerRequest) throws DataAccessException {
        if (registerRequest.username() == null || registerRequest.password() == null || registerRequest.email() == null) {
            throw new DataAccessException("bad request");
        }
        if(userDataAccess.getUser(registerRequest.username()) != null){
            throw new DataAccessException("already taken");
        }
        UserData user = new UserData(registerRequest.username(),registerRequest.password(),registerRequest.email());
        userDataAccess.createUser(user);
        AuthData auth = authDataAccess.createAuth(registerRequest.username());
        return new RegisterResult(auth.getUsername(),auth.getAuthToken());
    }
    public LoginResult login(LoginRequest loginRequest) throws DataAccessException {
        if (loginRequest.username() == null || loginRequest.password() == null) {
            throw new DataAccessException("bad request");
        }
        UserData user = userDataAccess.getUser(loginRequest.username());
        if(user == null){
            throw new DataAccessException("unauthorized");
        } else if (!userDataAccess.verifyUser(user, loginRequest.password())) {
            throw new DataAccessException("unauthorized");
        }
        AuthData auth = authDataAccess.createAuth(user.getUsername());
        return new LoginResult(user.getUsername(),auth.getAuthToken());
    }
    public void logout(LogoutRequest logoutRequest) throws DataAccessException {
        if(authDataAccess.getSession(logoutRequest.authToken()) == null){
            throw new DataAccessException("unauthorized");
        }
        authDataAccess.deleteSession(logoutRequest.authToken());
    }
    public void clear() throws DataAccessException {
        userDataAccess.clear();
        authDataAccess.clear();
        gameDataAccess.clear();
    }
}

