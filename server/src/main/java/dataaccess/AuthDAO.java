package dataaccess;

import model.*;

public class AuthDAO {
    public AuthData createAuth(String username) throws DataAccessException{
        throw new DataAccessException("not connected");
    }
    public AuthData getSession(String token) throws DataAccessException{
        return null;
    }
    public void deleteSession(String token) throws DataAccessException{}
    public void clear() throws DataAccessException{}
}
