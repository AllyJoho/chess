package dataaccess;

import model.UserData;

public class UserDAO {
    void getUser(UserData u) throws DataAccessException{}
    UserData createUser(UserData u) throws DataAccessException{
        return u;
    }
    void createAuth(UserData u) throws DataAccessException{}
    void getSession(String token) throws DataAccessException{}
    void deleteSession(String token) throws DataAccessException{}
    void clear() throws DataAccessException{}
}
