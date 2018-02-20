package myProg.services;

import myProg.jpa.entity.AbonEntity;
import org.springframework.lang.NonNull;

import java.util.List;


public interface AbonService {

    @NonNull
    List<AbonEntity> findAll();

    @NonNull
    AbonEntity findById(Long id);

    @NonNull
    Long count();
}
