package dataaccess;

import model.*;

public class AuthDAO {
    AuthData createAuth(AuthData a) throws DataAccessException{
        throw new DataAccessException("not connected");
    }
    AuthData getSession(String token) throws DataAccessException{
        return null;
    }
    void deleteSession(String token) throws DataAccessException{}
    void clear() throws DataAccessException{}
}
