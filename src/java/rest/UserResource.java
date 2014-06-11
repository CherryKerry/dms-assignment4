package rest;

import beans.UserBean;
import entities.UserEntity;
import java.util.List;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * The implementation of the restful service for the system
 * @author Kerry Powell
 */
@ManagedBean
@Path("/users")
public class UserResource
{
    @EJB
    private UserBean users;

    /**
     * Get the list of users from the system
     * @return 
     */
    @GET
    @Produces("text/plain")
    public String getUsers() {  
        StringBuilder buffer = new StringBuilder();
        List<UserEntity> userlist = users.getUsers();
        if (users == null)
            return "There are no users";
        for (UserEntity user : users.getUsers()) {
            buffer.append(user.getFirstName() + " ");
            buffer.append(user.getLastName() + " has ");
            buffer.append(user.getPoints() + " points\n");
        }
        return buffer.toString();
    }

    /**
     * Get the details of a selected user
     * 
     * @param firstName the name of the user
     * @param lastName the last name of the user
     * @return the users details of 'No such user' if not found
     */
    @Path("{firstName}/{lastName}")
    @GET
    @Produces("text/plain")
    public String getUser(@PathParam("firstName") String firstName, @PathParam("lastName") String lastName) { 
        UserEntity user = users.getUser(firstName, lastName);
        if (user != null)
            return firstName + " " + lastName + " has " + user.getPoints() + 
                    " points";
        else
            return "No such user";
    }
    
    /**
     * Get the points of a user
     * 
     * @param firstName the name of the user 
     * @param lastName the last name of the user
     * @return the points the user has or -1 if not found
     */
    @Path("{firstName}/{lastName}/points")
    @GET
    @Produces("text/plain")
    public long getUserPoints(@PathParam("firstName") String firstName, @PathParam("lastName") String lastName) { 
        UserEntity user = users.getUser(firstName, lastName);
        if (user != null)
            return user.getPoints();
        else
            return -1;
    }

    /**
     * Update the points of the user
     * 
     * @param firstName the name of the user
     * @param lastName the last name of the user
     * @param points the amount to set the points for the user to
     * @return the updated user, or 'no such user' if not found
     */
    @Path("{firstName}/{lastName}/points/{value}")
    @POST
    @Produces("text/plain")
    public String putUserPoints(@PathParam("firstName") String firstName, @PathParam("lastName") String lastName, @PathParam("value") long points) {  
        UserEntity user = users.updateUserPoints(firstName, lastName, points);
        if (user != null)
            return firstName + " " + lastName + " has " + user.getPoints() + 
                    " points";
        else
            return "No such user";
    }
}

