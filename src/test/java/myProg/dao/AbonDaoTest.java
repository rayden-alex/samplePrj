package myProg.dao;

import lombok.extern.slf4j.Slf4j;
import myProg.config.DataBaseConfig;
import myProg.domain.Abon;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlScriptsTestExecutionListener;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringJUnitConfig({DataBaseConfig.class})

// Spring provides the following TestExecutionListener implementations that are registered by default, exactly in this order.
// ServletTestExecutionListener: configures Servlet API mocks for a WebApplicationContext
// DirtiesContextBeforeModesTestExecutionListener: handles the @DirtiesContext annotation for before modes
// DependencyInjectionTestExecutionListener: provides dependency injection for the test instance
// DirtiesContextTestExecutionListener: handles the @DirtiesContext annotation for after modes
// TransactionalTestExecutionListener: provides transactional test execution with default rollback semantics
// SqlScriptsTestExecutionListener: executes SQL scripts configured via the @Sql annotation

// If a custom TestExecutionListener is registered via @TestExecutionListeners, the default listeners will not be registered.
// https://docs.spring.io/spring/docs/current/spring-framework-reference/testing.html#testcontext-tel-config-merging

// To avoid having to be aware of and re-declare all default listeners,
// the mergeMode attribute of @TestExecutionListeners can be set to MergeMode.MERGE_WITH_DEFAULTS.
@TestExecutionListeners(value = {
        DependencyInjectionTestExecutionListener.class, //  по дефолту этот Listener и так подключен, но если нужно подключить еще что-то, то надо указывать полный список или менять mergeMode на MERGE_WITH_DEFAULTS
        SqlScriptsTestExecutionListener.class
})

@ActiveProfiles({"default", "test"})
@TestPropertySource("classpath:jdbc-test.properties")
@Slf4j
class AbonDaoTest {
    @PersistenceUnit
    private EntityManagerFactory emf;

    @Autowired
    private AbonDao abonDao;

    @Test
    @Sql(statements = {"ALTER SEQUENCE ABON_ID_SEQ RESTART WITH 0"})
    void abonBatchSequenceInsertTest() {
        abonDao.deleteAllInBatch();

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        int count = 100;
        List<Abon> abonList = new ArrayList<>(count);
        tx.begin();
        try {
            for (int i = 0; i < count; i++) {
                Abon abon = new Abon();
                abonList.add(abon);

                em.persist(abon);
            }
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        }

        assertEquals(abonList.size(), abonDao.count());
        //log.info(abonList.toString());
    }
}
