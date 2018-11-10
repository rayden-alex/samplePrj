package myProg.dao;

import myProg.domain.Region;
import org.springframework.lang.Nullable;

public interface RegionDao extends MyJpaRepository<Region, Short> {
    // Returns null when the query executed does not produce a result.
    @Nullable
    Region findRegionById(@Nullable Short id);// Accepts null as the value for "id"
}
