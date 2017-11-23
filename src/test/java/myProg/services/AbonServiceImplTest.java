package myProg.services;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import myProg.AppConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;


//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {AppConfig.class})   ---------->   @SpringJUnitConfig(AppConfig.class)

@SpringJUnitWebConfig(AppConfig.class)

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

@ActiveProfiles({"default", "dev"})
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
        Assertions.assertEquals(653191L, cnt.longValue());
    }
}

