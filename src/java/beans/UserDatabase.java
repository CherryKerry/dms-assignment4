package beans;

import entities.UserEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 * Class for storing the data of the users
 * 
 * @author Kerry Powell
 */
@Singleton
@Startup
public class UserDatabase {
    
    private List<UserEntity> database = new ArrayList<UserEntity>();
    
    @PostConstruct
    public void setupTestUserData() {
         
        add(new UserEntity("Patrick", "Mercy"));
        add(new UserEntity("Haylee", "Ramsey"));
        add(new UserEntity("Dana", "Franklyn"));
        add(new UserEntity("Geoff", "Harrison"));
    }
    
    public boolean add(UserEntity user) {
        if (contains(user)) 
            return false;
        else
            return database.add(user);
    }
    
    public boolean update(UserEntity user) {
        for (int i = 0; i < database.size(); i++) {
            if (user.getName().equals(database.get(i).getName())) {
                database.get(i).setPoints(user.getPoints());
                return true;
            }
        }
        return false;
    }
    
    public boolean contains(UserEntity user) {
        for (UserEntity entry : database) {
            if (entry.getName().equals(user.getName()))
                return true;
        }
        return false;
    }

    public List<UserEntity> getUsers() {
        return database;
    }
    
}
