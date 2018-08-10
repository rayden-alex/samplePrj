package myProg.dao;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.Serializable;

/**
 * Repository extension to implement custom logic
 */
// Spring Data should not create instances at runtime
// see @EnableJpaRepositories("myProg.dao") in myProg/config/DataBaseConfig.java
@NoRepositoryBean
public class MyJpaRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements MyJpaRepository<T, ID> {
    private final EntityManager entityManager;

    MyJpaRepositoryImpl(@NonNull JpaEntityInformation<T, ID> entityInformation, @NonNull EntityManager entityManager) {
        super(entityInformation, entityManager);

        // Keep the EntityManager around to used from the newly introduced methods.
        this.entityManager = entityManager;
    }

    /**
     * Unlike "save" forces insert entity to DB without checking
     * it existence by PK
     */
    @Override
    @Transactional
    public <S extends T> S insert(S entity) {
        entityManager.persist(entity);
        return entity;
    }
}