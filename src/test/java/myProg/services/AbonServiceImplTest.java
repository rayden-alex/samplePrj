package myProg.services;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import lombok.extern.slf4j.Slf4j;
import myProg.config.AppConfig;
import myProg.domain.Abon;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {AppConfig.class})   ---------->   @SpringJUnitConfig(AppConfig.class)

@SpringJUnitConfig(AppConfig.class)

//Spring provides the following TestExecutionListener implementations that are registered by default, exactly in this order.
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
        DbUnitTestExecutionListener.class
}, mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS)

@ActiveProfiles({"default", "test"})
@Slf4j
//@Disabled
//@DbUnitConfiguration(databaseConnection={"testDataSource"})   // mast be "dataSource" bean or set it explicitly
class AbonServiceImplTest {
    private AbonService service;

    @Autowired
    public void setService(AbonService service) {
        this.service = service;
    }

    @Test
    public void countTest() {
        Long cnt = service.count();

        log.info("SLF4 test");
        assertEquals(653191L, cnt.longValue());
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
        try {
            service.findById(null);
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getCause().getClass());
        }
    }
}

