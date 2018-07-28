package myProg.services;

import myProg.domain.Abon;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;
import java.util.Optional;


public interface AbonService {

    @NonNull
    List<Abon> findAll();

    //@Nullable
    Optional<Abon> findById(Long id);

    @NonNull
    Long count();
}
