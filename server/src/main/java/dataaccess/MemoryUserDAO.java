package dataaccess;

import model.UserData;

import java.util.HashMap;

public class MemoryUserDAO extends UserDAO{
    private int nextId = 1;
    final private HashMap<Integer, UserData> users = new HashMap<>();
    public MemoryUserDAO(){

    }
    void getUser(UserData u) throws DataAccessException{
        return;
    }
    public UserData createUser(UserData u) throws DataAccessException{
        UserData user = new UserData(u.getUsername(), u.getPassword(), u.getEmail());
        users.put(nextId++, user);
        return user;
    }
    void createAuth(UserData u) throws DataAccessException{
        return;
    }
    void getSession(String token) throws DataAccessException{
        return;
    }
    void deleteSession(String token) throws DataAccessException{
        return;
    }
    void clear() throws DataAccessException{
        users.clear();
        nextId = 1;
    }
}
