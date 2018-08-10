package myProg.csv.readers;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import lombok.extern.slf4j.Slf4j;
import myProg.config.AppConfig;
import myProg.csv.entries.AbonEntry;
import myProg.csv.processors.AbonEntryProcessor;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@Slf4j

@ActiveProfiles({"default", "test"})
@TestPropertySource("classpath:jdbc-test.properties")
@SpringJUnitConfig(AppConfig.class)
@ExtendWith(MockitoExtension.class)
class AbonReaderTest {
    @Autowired
    private CsvMapper mapperBean;

    @Autowired
    private AbonReader readerBean;

    @Autowired
    private DataSource dataSourceBean;

    @Mock
    private AbonEntryProcessor entryProcessorMock;

    @Spy
    private AbonEntryProcessorStub entryProcessorStub;

    @BeforeEach
    void setUp() {
    }

    @Test
    void jdbcPropertiesTest() {
        final String expectedUrl = "jdbc:firebirdsql://localhost:3050/d:\\DB\\FB_BASES_TEST.FDB";
        String actualUrl = dataSourceBean.getUrl();

        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    void readFromFileMockTest() throws IOException {
        AbonReader reader = new AbonReader(mapperBean, entryProcessorMock);

        // реализация мока с подменой НЕ РЕЗУЛЬТАТА вызова метода,
        // а с подменой самой РЕАЛИЗАЦИИ метода
        doAnswer(invocation -> {
            AbonEntry entry = invocation.getArgument(0);
            log.info(entry.toString());
            return null;
        }).when(entryProcessorMock).process(any(AbonEntry.class));


        reader.readFromFile("d:/ascr/v_20170517174250/abon_test.csv");
        verify(entryProcessorMock, times(52)).process(any(AbonEntry.class));
    }

    @Test
    void readFromFileSpyTest() throws IOException {
        // подмена реализации метода через spy-объект
        AbonReader reader = new AbonReader(mapperBean, entryProcessorStub);

        reader.readFromFile("d:/ascr/v_20170517174250/abon_test.csv");
        verify(entryProcessorStub, times(52)).process(any(AbonEntry.class));
    }

    static abstract class AbonEntryProcessorStub extends AbonEntryProcessor {
        @Override
        public void process(AbonEntry entry) {
            log.info(entry.toString());
        }
    }

    @Test
    void readFromFileBeanTest() throws IOException {
        readerBean.readFromFile("d:/ascr/v_20170517174250/abon_test.csv");
        readerBean.saveToDB();
    }
}