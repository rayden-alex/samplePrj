package myProg.services;

import myProg.jpa.AbonEntity;
import org.springframework.lang.NonNull;

import java.util.List;


public interface AbonService {

    @NonNull
    List<AbonEntity> findAll();

    @NonNull
    List<AbonEntity> findById(Long id);

    @NonNull
    Long count();
}
