package myProg;

import myProg.jpa.AbonEntity;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.lang.NonNull;

import java.util.List;


public interface AbonDao {
    @NonNull
    List<AbonEntity> findAll();

    @NonNull
    List<AbonEntity> findAbonEntityById(Long id);

    List<Abon> findByFio(String fio);

    @NonNull
    List<Abon> findAbonById(Long id);

    @NonNull
    Long count();

    List<Abon> findFioById(Long id);
    void writeFioById(Long id, RowCallbackHandler rch);

//        List<Abon> findAllWithDetail();
//
//        void insert(Abon contact);
//
//        void insertWithDetail(Abon contact);
//
//        void update(Abon contact);
}
