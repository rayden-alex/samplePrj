package myProg;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository("abonDaoBean")
@Slf4j
public class AbonDaoImpl implements AbonDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final JdbcStream jdbcStream;

    @Autowired
    public AbonDaoImpl(@Qualifier("namedParameterJdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcStream = new JdbcStream(jdbcTemplate);
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
    public List<Abon> findAbonById(Long id) {
        Objects.requireNonNull(jdbcTemplate);

        final String SQL = "SELECT ID, FIO, PHONE_LOCAL FROM ABON WHERE ID < :ID";
        SqlParameterSource namedParameters = new MapSqlParameterSource("ID", id);

        return jdbcTemplate.query(SQL, namedParameters, getAbonRowMapper());
    }

    @Override
    public List<Abon> findFioById(Long id) {
        Objects.requireNonNull(jdbcTemplate);

        //language=SQL92
        final String SQL = "SELECT ID, FIO, PHONE_LOCAL FROM ABON WHERE ID <:ID1";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("ID1", id);

        return jdbcStream.streamQuery(SQL, namedParameters, stream -> stream
                .map(sqlRowSetAbonMapper())
                .collect(Collectors.toList()));


    }

    private Function<SqlRowSet, Abon> sqlRowSetAbonMapper() {
        return row -> {
            Abon abon = new Abon();

            abon.setId(row.getLong("ID"));
            abon.setFio(row.getString("FIO"));
            abon.setPhone(row.getString("PHONE_LOCAL"));

            return abon;
        };
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


    @Override
    public void writeFioById(Long id, RowCallbackHandler rch) {
        Objects.requireNonNull(jdbcTemplate);

        //language=SQL92
        final String SQL = "SELECT ID, FIO, PHONE_LOCAL FROM ABON WHERE ID <:ID1";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("ID1", id);

        //StreamingStatementCreator creator = new StreamingStatementCreator(SQL);

        jdbcTemplate.query(SQL, namedParameters, rch);
    }
}
