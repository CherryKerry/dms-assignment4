package beans;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import entities.UserEntity;
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
        try {            
            em.persist(new UserEntity("Patrick", "Mercy"));
            em.persist(new UserEntity("Haylee", "Ramsey"));
            em.persist(new UserEntity("Dana", "Franklyn"));
            em.persist(new UserEntity("Geoff", "Harrison"));
        } catch (EntityExistsException ex) {
            System.out.println("Entities already added");
        }
    }
    
}
