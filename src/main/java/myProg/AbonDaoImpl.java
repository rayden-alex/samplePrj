package myProg;

import lombok.extern.slf4j.Slf4j;
import myProg.jpa.AbonEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


//        you need to annotate your repository with @Repository so
//        PersistenceExceptionTranslationPostProcessor knows that this is a bean for which
//        exceptions should be translated into one of Spring’s unified data-access exceptions.
//        Speaking of PersistenceExceptionTranslationPostProcessor, you need to
//        remember to wire it up as a bean in Spring just as you did for the Hibernate example:
//        @Bean
//        public BeanPostProcessor persistenceTranslation() {
//        return new PersistenceExceptionTranslationPostProcessor();
//        }
//        Note that exception translation, whether with JPA or Hibernate, isn’t mandatory. If
//        you’d prefer that your repository throw JPA-specific or Hibernate-specific exceptions,
//        you’re welcome to forgo PersistenceExceptionTranslationPostProcessor and let
//        the native exceptions flow freely. But if you do use Spring’s exception translation,
//        you’ll be unifying all of your data-access exceptions under Spring’s exception hierar-
//        chy, which will make it easier to swap out persistence mechanisms later.

@Repository("abonDaoBean")
@Slf4j
public class AbonDaoImpl implements AbonDao {


    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final JdbcStream jdbcStream;


    private EntityManager entityManager;

    @PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Autowired
    public AbonDaoImpl(@Qualifier("namedParameterJdbcTemplate") NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.jdbcStream = new JdbcStream(jdbcTemplate);
    }

    @NonNull
    @Override
    @Transactional(readOnly = true)
    public List<AbonEntity> findAll() {
        TypedQuery<AbonEntity> query = entityManager.createQuery("from AbonEntity as a where a.id < :param", AbonEntity.class);
        query.setParameter("param", 100L);
        return query.getResultList();
    }

    @NonNull
    @Override
    @Transactional(readOnly = true)
    public List<AbonEntity> findAbonEntityById(Long id) {
        TypedQuery<AbonEntity> query = entityManager.createQuery("from AbonEntity as a where a.id = :param", AbonEntity.class);
        query.setParameter("param", id);
        return query.getResultList();
    }

    @Override
    public List<Abon> findByFio(String fio) {
        return null;
    }

    @NonNull
    @Override
    public List<Abon> findAbonById(Long id) {
        Objects.requireNonNull(jdbcTemplate);

        final String SQL = "SELECT ID, FIO, PHONE_LOCAL FROM ABON WHERE ID < :ID";
        SqlParameterSource namedParameters = new MapSqlParameterSource("ID", id);

        return jdbcTemplate.query(SQL, namedParameters, getAbonRowMapper());
    }

    @NonNull
    @Override
    @Transactional(readOnly = true)
    public Long count() {
        TypedQuery<Long> query =entityManager.createQuery("select count(a) from AbonEntity as a", Long.class);
        return query.getSingleResult();
    }

    @Override
    public List<Abon> findFioById(Long id) {
        Objects.requireNonNull(jdbcTemplate);

        //language=SQL92
        final String SQL = "SELECT ID, FIO, PHONE_LOCAL FROM ABON WHERE ID <:ID1";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("ID1", id);

        return jdbcStream.streamQuery(SQL, namedParameters, stream -> stream
                .map(this::sqlRowSetAbonMapper)
                .collect(Collectors.toList()));


    }

    private Abon sqlRowSetAbonMapper(SqlRowSet row) {
        Abon abon = new Abon();

        abon.setId(row.getLong("ID"));
        abon.setFio(row.getString("FIO"));
        abon.setPhone(row.getString("PHONE_LOCAL"));

        return abon;
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
