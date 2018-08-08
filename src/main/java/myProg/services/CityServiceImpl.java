package myProg.services;

import lombok.extern.slf4j.Slf4j;
import myProg.dao.CityDao;
import myProg.dao.CityTypeDao;
import myProg.domain.City;
import myProg.dto.CityWithType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Map;

@Service
@Slf4j
public class CityServiceImpl implements CityService {
    private final CityDao cityDao;
    private final CityTypeDao cityTypeDao;

    @Autowired
    public CityServiceImpl(CityDao cityDao, CityTypeDao cityTypeDao) {
        this.cityDao = cityDao;
        this.cityTypeDao = cityTypeDao;
    }


    @Override
    @Transactional
    public void saveAll(Map<CityWithType, Integer> cityMap) {
        log.info("Saving City list to DB with size={}", cityMap.size());

        cityMap.forEach((k, v) -> {
            City city = new City();
            city.setId(v);
            city.setCityType(cityTypeDao.getOne(k.getType()));
            city.setName(k.getName());

            // todo Create custom repository to access entityManager
            // and use "persist" instead of "save"
            // https://stackoverflow.com/questions/30874214/how-to-access-entity-manager-with-spring-boot-and-spring-data
            cityDao.save(city);
        });
    }

    @Override
    @Transactional
    public void deleteAllInBatch() {
        cityDao.deleteAllInBatch();
    }
}
