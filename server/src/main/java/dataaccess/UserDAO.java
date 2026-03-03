package dataaccess;

import model.UserData;

public class UserDAO {
    public UserData createUser(UserData u) throws DataAccessException{return u;}
    public UserData getUser(String username, String password) throws DataAccessException{return null;}
    public void clear() throws DataAccessException{
    }
}
