package myProg.services;

import myProg.AbonDao;
import myProg.jpa.AbonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AbonServiceImpl implements AbonService {
    private final AbonDao abonDao;

    @Autowired
    public AbonServiceImpl(AbonDao abonDao) {
        this.abonDao = abonDao;
    }


    @NonNull
    @Override
    public List<AbonEntity> findAll() {
        return abonDao.findAll();
    }

    @NonNull
    @Override
    public List<AbonEntity> findById(Long id) {
        return abonDao.findAbonEntityById(id);
    }

    @Override
    @NonNull
    public Long count() {
        return abonDao.count();

    }
}
