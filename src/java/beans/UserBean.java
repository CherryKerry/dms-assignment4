package beans;

import entities.User;
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
        sendMessage(new User(firstName, lastName));
        return true;
    }
    
    private void sendMessage(User user) {
        
        try (JMSContext context = connectionFactory.createContext();) {
            System.out.println("Sending message: " + user); 
            context.createProducer().send(queue, user);
        }
    }
        
    public User[] getUsers() {
        User[] users = dao.getUsers().toArray(new User[0]);
        return users;
    }

    public User getUser(String firstName, String lastName) {
        for (User user: dao.getUsers()) {
            if (firstName.equals(user.getFirstName()) && lastName.equals(user.getLastName())) {
                return user;
            }
        }
        return null;
    }
    
    public User updateUserPoints(String firstName, String lastName, long points) {
        for (User user: dao.getUsers()) {
            if (firstName.equals(user.getFirstName()) && lastName.equals(user.getLastName())) {
                user.setPoints(points);
                sendMessage(user);
                return user;
            }
        }
        return null;
    }   
}
