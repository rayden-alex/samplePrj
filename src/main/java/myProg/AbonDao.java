package myProg;

import java.util.List;


public interface AbonDao {
    List<Abon> findAll();

    List<Abon> findByFio(String fio);

    List<Abon> findFioById(Long id);

//        List<Abon> findAllWithDetail();
//
//        void insert(Abon contact);
//
//        void insertWithDetail(Abon contact);
//
//        void update(Abon contact);
}
