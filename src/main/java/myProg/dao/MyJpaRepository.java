package myProg.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MyJpaRepository<T, ID> extends JpaRepository<T, ID> {
    /**
     * Unlike "save" forces insert entity to DB without checking
     * it existence by PK
     */
    <S extends T> S insert(S entity);
}
