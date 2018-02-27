package myProg.services;

import myProg.jpa.entity.AbonEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;


public interface AbonService {

    @NonNull
    List<AbonEntity> findAll();

    @Nullable
    AbonEntity findById(Long id);

    @NonNull
    Long count();
}
