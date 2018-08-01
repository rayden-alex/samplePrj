package myProg.services;

import java.util.Map;

public interface CityTypeService {
    void saveAll(Map<String, Short> cityType);

    void deleteAllInBatch();
}
