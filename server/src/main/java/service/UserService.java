package service;
import dataaccess.*;
import model.*;
import request.*;
import result.*;

public class UserService {
    private final UserDAO userDataAccess;
    private final AuthDAO authDataAccess;

    public UserService(UserDAO userDataAccess, AuthDAO authDataAccess) {
        this.userDataAccess = userDataAccess;
        this.authDataAccess = authDataAccess;
    }
    public RegisterResult register(RegisterRequest registerRequest) throws DataAccessException {
        UserData user = new UserData(registerRequest.username(),registerRequest.password(),registerRequest.email());
        userDataAccess.createUser(user);
        AuthData auth = authDataAccess.createAuth(registerRequest.username());
        return new RegisterResult(auth.getUsername(),auth.getAuthToken());
    }
    public LoginResult login(LoginRequest loginRequest) {
        return null;
    }
    public void logout(LogoutRequest logoutRequest) {

    }
}

