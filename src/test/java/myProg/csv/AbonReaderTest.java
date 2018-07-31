package myProg.csv;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import myProg.csv.entries.AbonEntry;
import myProg.csv.processors.EntryProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;


@Slf4j
@ExtendWith(MockitoExtension.class)
class AbonReaderTest {
    private CsvMapper mapper;
    private AbonReader reader;

    @Mock
    private EntryProcessor<AbonEntry> entryProcessor;

    @BeforeEach
    void setUp() {
        mapper = (CsvMapper) new CsvMapper().registerModule(new JavaTimeModule());
        reader = new AbonReader(mapper, entryProcessor);
    }

    @Test
    void readFromFileTest() {

        doAnswer(invocation -> {
            AbonEntry abonEntry = invocation.getArgument(0);
            System.out.println(abonEntry);
            return null;
        }).when(entryProcessor).process(any(AbonEntry.class));


        try {
            reader.readFromFile("d:/ascr/v_20170517174250/abon_test.csv");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}