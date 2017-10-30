package myProg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;

@Repository("abonDaoBean")
@Slf4j
public class AbonDaoImpl implements AbonDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public AbonDaoImpl(@Qualifier("namedParameterJdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

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
        Objects.requireNonNull(jdbcTemplate);

        final String SQL = "SELECT ID, FIO, PHONE_LOCAL FROM ABON WHERE ID = :ID";
        SqlParameterSource namedParameters = new MapSqlParameterSource("ID", id);

        return jdbcTemplate.query(SQL, namedParameters, getAbonRowMapper());

    }

    private RowMapper<Abon> getAbonRowMapper() {
        return (rs, rowNum) -> {
            Abon abon = new Abon();

            abon.setId(rs.getLong("ID"));
            abon.setFio(rs.getString("FIO"));
            abon.setPhone(rs.getString("PHONE_LOCAL"));

            return abon;
        };
    }
}
