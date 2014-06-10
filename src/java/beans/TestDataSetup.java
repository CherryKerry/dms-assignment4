package beans;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import entities.User;
import javax.persistence.EntityExistsException;

/**
 * Creates initial data for the server to test with
 * @author Kerry Powell
 */
@Singleton
@Startup
public class TestDataSetup {
    
    @PersistenceContext
    EntityManager em;
    
    @PostConstruct
    public void setupTestUserData() {
        /*
        try {            
            em.persist(new User("Patrick", "Mercy"));
            em.persist(new User("Haylee", "Ramsey"));
            em.persist(new User("Dana", "Franklyn"));
            em.persist(new User("Geoff", "Harrison"));
        } catch (EntityExistsException ex) {
            System.out.println("Entities already added");
        }
                */
    }
    
}
