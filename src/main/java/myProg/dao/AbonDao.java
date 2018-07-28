package myProg.dao;

import myProg.domain.Abon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AbonDao extends JpaRepository<Abon, Long> {
}
