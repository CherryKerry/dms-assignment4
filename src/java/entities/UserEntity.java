package entities;

/**
 * A class to represent the users data along with convenience methods for 
 * formatting and comparisons
 * 
 * @author Kerry Powell
 */
public class UserEntity{
    
    private String name;
    private String firstName;
    private String lastName;
    private long points;

    public UserEntity() {
        firstName = "";
        lastName = "";
        points = 0;
    }
    
    public UserEntity(String firstName, String lastName) {
        if (lastName == null || firstName == null)
            throw new NullPointerException("First and/or Last name cannot be null");
        setFirstName(firstName);
        setLastName(lastName);
        points = 0;
    }
    
    private String proper(String word) {
        if (word.length() > 1) {
            String proper = word.substring(0, 1).toUpperCase();
            proper += word.substring(1, word.length());
            return proper;
        } else {
            return word;
        }
    }

    public String getFirstName() {
        return proper(firstName);
    }

    public void setFirstName(String firstName) {
        firstName = firstName.toLowerCase();
        this.firstName = firstName;
        name = firstName + " " + lastName;
    }

    public String getLastName() {
        return proper(lastName);
    }
    
    public String getName() {
        return name;
    }

    public void setLastName(String lastName) {
        lastName = lastName.toLowerCase();
        this.lastName = lastName;
        name = firstName + " " + lastName;
    }

    public long getPoints() {
        return points;
    }

    public void setPoints(long points) {
        this.points = points;
    }
    
    public boolean equals(UserEntity user) {
        String myName = name.toLowerCase();
        String otherName = user.getName().toLowerCase();
        return myName.equals(otherName);
    }
    
    @Override
    public String toString() {
        return firstName + "\n" + lastName + "\n" + points;
    }
}
