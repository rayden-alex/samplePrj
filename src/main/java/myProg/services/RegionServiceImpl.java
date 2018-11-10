package myProg.services;

import myProg.dao.RegionDao;
import myProg.domain.Region;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegionServiceImpl implements RegionService {
    private final RegionDao regionDao;

    @Autowired
    public RegionServiceImpl(RegionDao regionDao) {
        this.regionDao = regionDao;
    }

    @Override
    public long count() {
        return regionDao.count();
    }

    @Override
    public Optional<Region> findById(@NonNull Short id) {
        return regionDao.findById(id);
    }

    @Override
    @Nullable
    public Region findRegionById(@Nullable Short id) {
        return regionDao.findRegionById(id);
    }

    @Override
    public List<Region> findAll() {
        return regionDao.findAll();
    }
}
