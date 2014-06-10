package entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * A class to represent the users data
 * @author Kerry Powell
 */
@Entity
public class User implements Serializable{
    
    @Id
    private String name;
    private String firstName;
    private String lastName;
    private long points;

    public User() {
        firstName = "";
        lastName = "";
        points = 0;
    }
    
    public User(String firstName, String lastName) {
        if (lastName == null || firstName == null)
            throw new NullPointerException("First and/or Last name cannot be null");
        this.firstName = firstName;
        this.lastName = lastName;
        name = firstName + " " + lastName;
        points = 0;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
        name = firstName + " " + lastName;
    }

    public String getLastName() {
        return lastName;
    }
    
    public String getName() {
        return name;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
        name = firstName + " " + lastName;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }
    
    @Override
    public String toString() {
        return firstName + "\n" + lastName + "\n" + points;
    }
    
    public static User createUserFromString(String string) {
        String[] args = string.split("\n");
        User user = null;
        if (args.length == 3) {
            user = new User(args[0], args[1]);
            try {
                user.setPoints(Long.parseLong(args[2]));
            } catch (Exception ex) {}
        }
        return user;
    }
    
}
