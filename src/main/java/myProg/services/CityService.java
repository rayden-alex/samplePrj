package myProg.services;

import myProg.dto.CityWithType;

import java.util.Map;

public interface CityService {
    void saveAll(Map<CityWithType, Integer> city);

    void deleteAllInBatch();
}
