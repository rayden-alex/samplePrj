package myProg.dao;

import lombok.extern.slf4j.Slf4j;
import myProg.domain.Abon;
import myProg.dto.AbonDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.Collections;
import java.util.List;
import java.util.Objects;


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

//@Repository("abonDaoBean")
@Slf4j
public class AbonDaoImplOld { //implements AbonDao {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private EntityManager entityManager;

    //@PersistenceContext
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //@Autowired
    public AbonDaoImplOld(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @NonNull
    //@Override
    @Transactional(readOnly = true)
    public List<Abon> findAll() {
        TypedQuery<Abon> query = entityManager.createQuery("select a from Abon as a where a.id < :param", Abon.class);
        query.setParameter("param", 100L);
        query.setHint("org.hibernate.fetchSize", "200");
        return query.getResultList();
    }

    @Nullable
    //@Override
    @Transactional(readOnly = true)
    public Abon findAbonEntityById(Long id) {
        TypedQuery<Abon> query = entityManager.createQuery("select a from Abon as a where a.id = :param", Abon.class);
        query.setParameter("param", id);
        return DataAccessUtils.singleResult(query.getResultList());
    }

    //@Override
    public List<AbonDto> findByFio(String fio) {
        return Collections.emptyList();
    }

    @NonNull
    //@Override
    public List<AbonDto> findAbonById(Long id) {
        Objects.requireNonNull(jdbcTemplate);

        final String SQL = "SELECT ID, FIO, PHONE_LOCAL FROM ABON WHERE ID < :ID";
        SqlParameterSource namedParameters = new MapSqlParameterSource("ID", id);

        return jdbcTemplate.query(SQL, namedParameters, getAbonRowMapper());
    }

    @NonNull
    //@Override
    @Transactional(readOnly = true)
    public Long count() {
        TypedQuery<Long> query = entityManager.createQuery("select count(a) from Abon as a", Long.class);
        return query.getSingleResult();
    }

    //@Override
    public List<AbonDto> findFioById(Long id) {
        Objects.requireNonNull(jdbcTemplate);

        final String SQL = "SELECT ID, FIO, PHONE_LOCAL FROM ABON WHERE ID <:ID1";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("ID1", id);

        return jdbcTemplate.query(SQL, namedParameters, getAbonRowMapper());
    }

    private RowMapper<AbonDto> getAbonRowMapper() {
        return (rs, rowNum) -> new AbonDto(
                rs.getLong("ID"),
                rs.getString("FIO"),
                rs.getString("PHONE_LOCAL")
        );
    }


    //@Override
    public void writeFioById(Long id, RowCallbackHandler rch) {
        Objects.requireNonNull(jdbcTemplate);

        final String SQL = "SELECT ID, FIO, PHONE_LOCAL FROM ABON WHERE ID <:ID1";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("ID1", id);

        //StreamingStatementCreator creator = new StreamingStatementCreator(SQL);

        jdbcTemplate.query(SQL, namedParameters, rch);
    }
}
