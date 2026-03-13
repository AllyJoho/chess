package dataaccess;

import model.UserData;

public class UserDAO {
    public UserData createUser(UserData u) throws DataAccessException{return u;}
    public boolean verifyUser(UserData u, String password){return false;}
    public UserData getUser(String username) throws DataAccessException{return null;}
    public void clear() throws DataAccessException{
    }
}
