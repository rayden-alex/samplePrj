package myProg.services;

import myProg.dao.AbonDao;
import myProg.domain.Abon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AbonServiceImpl implements AbonService {
    private final AbonDao abonDao;

    @Autowired
    public AbonServiceImpl(AbonDao abonDao) {
        this.abonDao = abonDao;
    }


    @NonNull
    @Override
    public List<Abon> findAll() {
        return abonDao.findAll();
    }

    @Override
    @NonNull
    public Optional<Abon> findById(@NonNull Long id) {
        return abonDao.findById(id);
    }

    @Override
    @NonNull
    public Long count() {
        return abonDao.count();

    }
}
