package myProg.services;

import myProg.domain.Abon;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;


public interface AbonService {

    List<Abon> findAll();

    Optional<Abon> findById(@NonNull Long id);

    long count();

    @Nullable
    Abon findAbonById(@Nullable Long id);
}
