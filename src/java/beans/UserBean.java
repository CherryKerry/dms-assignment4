package beans;

import entities.UserEntity;
import java.util.List;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Singleton;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 * Create a new local user bean that operates between the UserDao (data access 
 * object) and the REST services
 * 
 * @author Kerry Powell
 */
@Singleton
@LocalBean
public class UserBean {
    
    @Resource(mappedName = "jms/connectionFactory")
    private ConnectionFactory connectionFactory;
    @Resource(lookup = "jms/myQueue")
    private Queue queue;
    @EJB 
    private UserDatabase database;
    
    /**
     * Create a new user and add them to the database
     * 
     * @param firstName the new users first name
     * @param lastName the new users last name
     * @return true if the user has been added
     */
    public boolean create(String firstName, String lastName) {
        sendMessage(new UserEntity(firstName, lastName));
        return true;
    }
    
    /**
     * Send a new user to the user DAO to be saved
     * @param user the user to add/update
     */
    private void sendMessage(UserEntity user) {
        try (JMSContext context = connectionFactory.createContext();) {
            System.out.println("Sending message: " + user);
            String message = user.getFirstName() + "\n" +
                    user.getLastName() + "\n" +
                    user.getPoints();
            context.createProducer().send(queue,  message);
        } 
    }
      
    /**
     * Get all the users form the database
     * @return list of users
     */
    public List<UserEntity> getUsers() {
        List<UserEntity> users = database.getUsers();
        return users; // new UserEntity[0]; //users;
    }

    /**
     * Get a specific user form the database
     * 
     * @param firstName the users first name
     * @param lastName the users last name
     * @return the user if found, else null
     */
    public UserEntity getUser(String firstName, String lastName) {
        UserEntity search = new UserEntity(firstName, lastName);
        for (UserEntity user: database.getUsers()) {
            if (search.equals(user)) {
                return user;
            }
        }
        return null;
    }
    
    /**
     * Update a users point in the system
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     * @param points the points to set as for the user
     * @return the users updated data, else null
     */
    public UserEntity updateUserPoints(String firstName, String lastName, long points) {
        UserEntity update = new UserEntity(firstName, lastName);
        if (getUser(firstName, lastName) != null) {
            update.setPoints(points);
            sendMessage(update);
            return update;
        }
        return null;
    }

    /**
     * Add a new entity to the database
     * @param firstName the first name of the entity
     * @param lastName the last name
     * @return the new entity
     */
    public UserEntity addUser(String firstName, String lastName) {
        UserEntity entity = new UserEntity(firstName, lastName);
        if (database.contains(entity)) {
            return null;
        }
        sendMessage(entity);
        return entity;
    }
}
