package myProg.services;

import myProg.dao.AbonDao;
import myProg.domain.Abon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Lazy
public class AbonServiceImpl implements AbonService {
    private final AbonDao abonDao;

    @Autowired
    public AbonServiceImpl(AbonDao abonDao) {
        this.abonDao = abonDao;
    }


    @Override
    public List<Abon> findAll() {
        return abonDao.findAll();
    }

    @Override
    public Optional<Abon> findById(@NonNull Long id) {
        return abonDao.findById(id); //Throws an IllegalArgumentException when the "id" handed to the method is null.
    }

    @Override
    public long count() {
        return abonDao.count();
    }

    @Override
    public Abon findAbonById(Long id) {
        return abonDao.findAbonById(id); // Accepts null as the value for "id"
    }
}
