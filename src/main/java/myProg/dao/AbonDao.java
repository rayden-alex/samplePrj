package myProg.dao;

import myProg.domain.Abon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public interface AbonDao extends JpaRepository<Abon, Long> {
    // Returns null when the query executed does not produce a result.
    @Nullable
    Abon findAbonById(@Nullable Long id);// Accepts null as the value for "id"

}
