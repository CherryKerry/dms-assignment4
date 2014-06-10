package beans;

import java.util.List;
import javax.annotation.Resource;
import javax.ejb.MessageDriven;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Topic;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import entities.User;

/**
 *
 * @author Kerry Powell
 */
@MessageDriven(mappedName="jms/myQueue")
public class UserDao implements MessageListener {
    @PersistenceContext
    EntityManager em;
    @Resource(lookup = "java:comp/DefaultJMSConnectionFactory")
    private static ConnectionFactory connectionFactory;
    @Resource(lookup = "jms/MyTopic")
    private static Topic topic;
    
    public UserDao() {
    }
    
    @Override
    public void onMessage(Message message) {
        User user = (User)message;
        try {
            //Try to add the user
            em.persist(user);
        } catch (EntityExistsException e) {
            //Try to update the users points
            em.getTransaction().begin();
            User dbuser = (User) em.find(User.class, user.getName());
            dbuser.setPoints(user.getPoints());
            em.getTransaction().commit();     
        }
    }
    
    public void sendMessageToTopic(User user) {
        try (JMSContext context = connectionFactory.createContext();) {
            System.out.println("Sending message: " + user); 
            context.createProducer().send(topic, user);
        }
    }
    
    public List<User> getUsers() {
        return em.createQuery("select u from User u", User.class).getResultList();
    }
}
