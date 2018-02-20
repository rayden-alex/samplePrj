package myProg.services;

import myProg.dao.AbonDao;
import myProg.jpa.entity.AbonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
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

    @Nullable
    @Override
    public AbonEntity findById(Long id) {
        return abonDao.findAbonEntityById(id);
    }

    @Override
    @NonNull
    public Long count() {
        return abonDao.count();

    }
}
