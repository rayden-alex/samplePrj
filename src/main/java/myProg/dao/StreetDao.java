package myProg.dao;

import myProg.domain.Street;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StreetDao extends JpaRepository<Street, Integer> {
}
