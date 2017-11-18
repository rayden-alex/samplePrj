package myProg.services;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import myProg.AppConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AppConfig.class})
@TestExecutionListeners({
        DependencyInjectionTestExecutionListener.class,
        DbUnitTestExecutionListener.class
})
@ActiveProfiles({"default", "dev"})
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

