package myProg.services;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.DbUnitConfiguration;
import lombok.extern.slf4j.Slf4j;
import myProg.config.AppConfig;
import myProg.config.DbUnitConfig;
import myProg.domain.Abon;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.context.transaction.BeforeTransaction;
import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {AppConfig.class})   ---------->   @SpringJUnitConfig(AppConfig.class)
@SpringJUnitConfig({AppConfig.class, DbUnitConfig.class})

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
        //  DependencyInjectionTestExecutionListener.class, //  по дефолту этот Listener и так подключен, но если нужно подключить еще что-то, то надо указывать полный список или менять mergeMode на MERGE_WITH_DEFAULTS
        DbUnitTestExecutionListener.class  //TransactionDbUnitTestExecutionListener.class
}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)

@ActiveProfiles({"default", "test"})
@TestPropertySource("classpath:jdbc-test.properties")
@Slf4j
//@Disabled

// https://www.petrikainulainen.net/programming/spring-framework/spring-data-jpa-tutorial-integration-testing/
@DatabaseSetup("classpath:db/abon_testdata.xml")


/**
 * @see DbUnitConfig#getDatabaseDataSourceConnectionFactoryBean()
 */
@DbUnitConfiguration(databaseConnection = "dbUnitDatabaseConnection")

/**
 * Annotating a test method with @Transactional causes the test to be run within a transaction
 * that will, by default, be automatically rolled back after completion of the test.
 * If a test class is annotated with @Transactional, each test method within that class hierarchy will be run within a transaction.
 * Test methods that are not annotated with @Transactional (at the class or method level) will not be run within a transaction.
 *
 * Чтобы видеть данные вставленные @DatabaseSetup("classpath:db/abon_testdata.xml") тестовый метод
 * должен выполнятся в той же транзакции,что и @DatabaseSetup (т.е. она должна начаться ДО выполнения тестового метода)
 * с этим как раз помогает @Transactional,
 * или должен быть включен автокоммит в настройках Datasource.
 * (Но тогда все вставленные данные надо чистить вручную в методе аннотированом @DatabaseTearDown)
 *
 * @see TransactionalTestExecutionListener
 */
@Transactional
class AbonServiceImplTest {
    private AbonService service;

    @Autowired
    void setService(AbonService service) {
        this.service = service;
    }

    @Autowired
    private DataSource dataSourceBean;

    @BeforeTransaction
    @Sql(statements = "alter table ABON alter column ID RESTART WITH 0;")
    void resetAbonAutoIncrementColumn() {
        //do nothing
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void jdbcPropertiesTest() {
        final String expectedUrl = "jdbc:firebirdsql://localhost:3050/d:\\DB\\FB_BASES_TEST.FDB";
        String actualUrl = dataSourceBean.getUrl();

        assertEquals(expectedUrl, actualUrl);
    }

    @Test
        //@Transactional
        //@Sql(statements = "alter table ABON alter column ID RESTART WITH 0;")
        //@Commit
    void countTest() {
        Long cnt = service.count();

        log.info("SLF4 countTest");
        assertEquals(100L, cnt.longValue());
    }

    @Test
    void findAbonByIdNotExistTest() {
        Abon abon = service.findAbonById(-1L);
        assertNull(abon);
    }

    @Test
    void findAbonByIdExistTest() {
        Abon abon = service.findAbonById(25L);
        assertNotNull(abon);
        assertEquals(25L, abon.getId().longValue());
    }

    @Test()
    void findAbonByIdNullTest() {
        Abon abon = service.findAbonById(null);
        assertNull(abon);
    }

    @Test
    void findByIdNotExistTest() {
        Optional<Abon> abon = service.findById(-1L);
        assertEquals(Optional.empty(), abon);
    }

    @Test
    void findByIdNullTest() {
        Class exp = IllegalArgumentException.class;

        try {
            service.findById(null);
        } catch (Throwable actualException) {
            if (exp.isInstance(actualException) || exp.isInstance(actualException.getCause())) {
                return;
            } else {
                fail("Unexpected exception type thrown", actualException);
            }
        }

        fail("Expected IllegalArgumentException to be thrown, but nothing was thrown.");
    }
}

