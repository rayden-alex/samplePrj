package myProg.dao;

import myProg.domain.Abon;
import org.springframework.lang.Nullable;

public interface AbonDao extends MyJpaRepository<Abon, Long> {
    // Returns null when the query executed does not produce a result.
    @Nullable
    Abon findAbonById(@Nullable Long id);// Accepts null as the value for "id"

}
