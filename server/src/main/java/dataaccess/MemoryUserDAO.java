package dataaccess;

import model.UserData;

import java.util.HashMap;
import java.util.Objects;

public class MemoryUserDAO extends UserDAO{
    final private HashMap<String, UserData> users = new HashMap<>();
    public MemoryUserDAO(){

    }
    public UserData getUser(String username) throws DataAccessException{
//        if(!users.containsKey(username)){
//            throw new DataAccessException("unauthorized");
//        }
        return users.get(username);
    }
    public boolean verifyUser(UserData u, String password){
        return Objects.equals(u.getPassword(), password);
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
