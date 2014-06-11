package beans;

import javax.ejb.MessageDriven;
import javax.jms.Message;
import javax.jms.MessageListener;
import entities.UserEntity;
import javax.ejb.EJB;
import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * Data Access Object for accessing the stored data in the sql database
 * @author Kerry Powell
 */
@MessageDriven(mappedName="jms/myQueue")
public class UserDao implements MessageListener {

    @EJB private UserDatabase database;
    
    @Override
    public void onMessage(Message message) {
        if (message instanceof TextMessage) {
            UserEntity user = getUser(message);
            if (database.contains(user)) {
                //If the databse contains the user, update their points
                database.update(user);
            } else if (user.getPoints() == 0) {
                //If the users points == 0 then add them to the database
                database.add(user);
            }
        } else {
            System.out.println("Received non-text message: " + message);
        }
    }
    
    /**
     * Extract the user from the message text
     * @param message the message that has been received
     * @return the user that has been created
     */
    private UserEntity getUser(Message message) {
        String text = null;
        try {
            text = ((TextMessage)message).getText();
        } catch (JMSException ex) {
            return null;
        }
        System.out.println("Received text message: " + text);
        String[] data = text.split("\n");
        if (data.length == 3) {
            UserEntity user = new UserEntity(data[0], data[1]);
            user.setPoints(Long.parseLong(data[2]));
            return user;
        } else {
            return null;
        }    
    }
}
