package beans;

import entities.UserEntity;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Queue;

/**
 * Create a new local user bean that operates between the UserDao (data access 
 * object) and the REST services
 * 
 * @author Kerry Powell
 */
@Stateless
@LocalBean
public class UserBean {
    
    @Resource(mappedName = "jms/connectionFactory")
    private static ConnectionFactory connectionFactory;
    @Resource(lookup = "jms/MyQueue")
    private static Queue queue;
    @EJB private UserDao dao;

    public boolean create(String firstName, String lastName) {
        sendMessage(new UserEntity(firstName, lastName));
        return true;
    }
    
    private void sendMessage(UserEntity user) {
        
        try (JMSContext context = connectionFactory.createContext();) {
            System.out.println("Sending message: " + user); 
            context.createProducer().send(queue, user);
        }
    }
        
    public UserEntity[] getUsers() {
        UserEntity[] users = dao.getUsers().toArray(new UserEntity[0]);
        return users;
    }

    public UserEntity getUser(String firstName, String lastName) {
        for (UserEntity user: dao.getUsers()) {
            if (firstName.equals(user.getFirstName()) && lastName.equals(user.getLastName())) {
                return user;
            }
        }
        return null;
    }
    
    public UserEntity updateUserPoints(String firstName, String lastName, long points) {
        for (UserEntity user: dao.getUsers()) {
            if (firstName.equals(user.getFirstName()) && lastName.equals(user.getLastName())) {
                user.setPoints(points);
                sendMessage(user);
                return user;
            }
        }
        return null;
    }   
}
