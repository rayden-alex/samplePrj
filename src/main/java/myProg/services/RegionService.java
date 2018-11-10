package myProg.services;

import myProg.domain.Region;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;

public interface RegionService {
    long count();

    Optional<Region> findById(@NonNull Short id);

    @Nullable
    Region findRegionById(@Nullable Short id);

    List<Region> findAll();
}
