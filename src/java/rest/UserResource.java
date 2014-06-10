package rest;

import beans.UserBean;
import entities.UserEntity;
import javax.annotation.ManagedBean;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@ManagedBean // so that dependency injection can be used for EJB
@Path("/users") // url path for StocksResource to handle
public class UserResource
{
    @EJB private UserBean users;

    /**
     * Get th list of users from the system
     * @return 
     */
    @GET
    @Produces("text/plain")
    public String getUsers() {  
       StringBuilder buffer = new StringBuilder();
        if (users == null)
            return "There are no users";
        for (UserEntity user : users.getUsers()) {
            buffer.append(user.getFirstName() + " ");
            buffer.append(user.getLastName() + " has ");
            buffer.append(user.getPoints() + " points\n");
        }
        return buffer.toString();
    }

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

    @Path("{firstName}/{lastName}/{value}")
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

