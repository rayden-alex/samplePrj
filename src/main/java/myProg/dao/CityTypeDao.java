package myProg.dao;

import myProg.domain.CityType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityTypeDao extends JpaRepository<CityType, Short> {
}
