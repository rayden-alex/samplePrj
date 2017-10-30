package myProg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import java.util.List;
import java.util.Objects;

@Slf4j
public class AbonDaoImpl extends JdbcDaoSupport implements AbonDao {

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
        JdbcTemplate jt = getJdbcTemplate();
        Objects.requireNonNull(jt);

        return jt.query("SELECT ID, FIO, PHONE_LOCAL FROM ABON WHERE ID = ?",

                ps -> ps.setLong(1, id),

                (rs, rowNum) -> {
                    Abon abon = new Abon();

                    abon.setId(rs.getLong("ID"));
                    abon.setFio(rs.getString("FIO"));
                    abon.setPhone(rs.getString("PHONE_LOCAL"));

                    return abon;
                }
        );

    }
}
