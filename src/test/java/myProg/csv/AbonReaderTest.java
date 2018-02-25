package myProg.csv;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;


@Slf4j
class AbonReaderTest {

    private CsvMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = (CsvMapper) new CsvMapper().registerModule(new JavaTimeModule());
    }

    @Test
    void readFromFile() {

        AbonReader reader = new AbonReader(mapper);

        try {
            List<AbonEntry> abons = reader.readFromFile("d:/ascr/v_20170517174250/abon_test.csv");
           log.info(abons.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}