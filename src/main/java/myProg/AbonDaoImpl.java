package myProg;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("abonDaoBean")
public class AbonDaoImpl implements AbonDao {
    private static final Logger LOG = LoggerFactory.getLogger(AbonDaoImpl.class);

    @Autowired()
    @Qualifier("dataSourceBean")
    private DataSource dataSource;

    @Override
    public List<Abon> findAll() {
        return null;
    }

    @Override
    public List<Abon> findByFio(String fio) {
        return null;
    }

    @Override
    public List<Abon> findFioById(Long id) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("ID", id);

        return new SelectAbonById(dataSource).executeByNamedParam(paramMap);
    }
}
