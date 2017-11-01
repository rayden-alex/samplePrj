package myProg;

import myProg.jpa.AbonEntity;
import org.springframework.jdbc.core.RowCallbackHandler;

import java.util.List;


public interface AbonDao {
    List<AbonEntity> findAll();

    List<Abon> findByFio(String fio);

    List<Abon> findAbonById(Long id);
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
