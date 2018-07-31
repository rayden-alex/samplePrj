package myProg.dao;

import myProg.domain.StreetType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreetTypeDao extends JpaRepository<StreetType, Short> {
}
