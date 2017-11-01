package myProg.springMvc;

import javax.persistence.EntityManager;

public interface EntityManagerAware {
    EntityManager getEntityManager();
}
