package dataaccess;

import model.UserData;

import java.util.HashMap;

public class MemoryUserDAO extends UserDAO{
    final private HashMap<String, UserData> users = new HashMap<>();
    public MemoryUserDAO(){

    }
    public UserData getUser(UserData u) throws DataAccessException{
        return users.get(u);
    }
    public UserData createUser(UserData u) throws DataAccessException{
        if(users.containsKey(u.getUsername())){
            throw new DataAccessException("username there already");
        }
        UserData user = new UserData(u.getUsername(), u.getPassword(), u.getEmail());
        users.put(u.getUsername(), user);
        return user;
    }
    public void clear() throws DataAccessException{
        users.clear();
    }
}
