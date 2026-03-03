package dataaccess;

import model.UserData;

import java.util.HashMap;
import java.util.Objects;

public class MemoryUserDAO extends UserDAO{
    final private HashMap<String, UserData> users = new HashMap<>();
    public MemoryUserDAO(){

    }
    public UserData getUser(String username, String password) throws DataAccessException{
        if(!users.containsKey(username)){
            throw new DataAccessException("unauthorized");
        }
        UserData user = users.get(username);
        if(!Objects.equals(user.getPassword(), password)){
            throw new DataAccessException("unauthorized");
        }
        return user;
    }
    public UserData createUser(UserData u) throws DataAccessException{
        if(users.containsKey(u.getUsername())){
            throw new DataAccessException("already taken");
        }
        UserData user = new UserData(u.getUsername(), u.getPassword(), u.getEmail());
        users.put(u.getUsername(), user);
        return user;
    }
    public void clear() throws DataAccessException{
        users.clear();
    }
}
