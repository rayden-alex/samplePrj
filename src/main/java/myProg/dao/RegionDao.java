package myProg.dao;

import myProg.domain.Region;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegionDao extends JpaRepository<Region, Short> {
}
