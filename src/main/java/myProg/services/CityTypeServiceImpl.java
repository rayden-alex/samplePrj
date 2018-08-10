package myProg.services;

import lombok.extern.slf4j.Slf4j;
import myProg.dao.CityTypeDao;
import myProg.domain.CityType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Service
@Lazy
@Slf4j
public class CityTypeServiceImpl implements CityTypeService {

    @Autowired
    private CityTypeDao cityTypeDao;

    @Override
    @Transactional
    public void saveAll(Map<String, Short> cityTypeMap) {
        log.info("Saving CityType list to DB with size={}", cityTypeMap.size());

        cityTypeMap.forEach((k, v) -> {
            CityType cityType = new CityType();
            cityType.setId(v);
            cityType.setName(k);
            cityType.setShortName("");

            cityTypeDao.insert(cityType);
        });
    }

    @Override
    @Transactional
    public void deleteAllInBatch() {
        cityTypeDao.deleteAllInBatch();
    }
}
