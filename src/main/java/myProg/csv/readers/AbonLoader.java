package myProg.csv.readers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
import myProg.csv.entries.AbonEntry;
import myProg.csv.processors.AbonEntryProcessor;
import myProg.services.CityService;
import myProg.services.CityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;

@Component
@Lazy
@Slf4j
public class AbonLoader implements RecordLoader {

    @Autowired
    private CityTypeService cityTypeService;

    @Autowired
    private CityService cityService;

    private static final String NULL_VALUE = "(null)";
    private static final char DELIMITER = ';';
    private static final Charset FILE_CHARSET = Charset.forName("windows-1251");


    private CsvMapper mapper;
    private CsvSchema schema;
    private AbonEntryProcessor entryProcessor;


    @Autowired
    public AbonLoader(@NonNull CsvMapper mapper, @NonNull AbonEntryProcessor entryProcessor) {
        log.info("{}: initialization started", getClass().getSimpleName());

        this.mapper = mapper;
        this.entryProcessor = entryProcessor;
        this.schema = buildSchema(mapper);

        log.info("{}: initialization completed", getClass().getSimpleName());
    }

    private ObjectReader buildObjectReader() {
        return mapper
                .readerFor(AbonEntry.class)
                .with(schema)
                .withFeatures(CsvParser.Feature.TRIM_SPACES)
                .withoutFeatures(JsonParser.Feature.AUTO_CLOSE_SOURCE);
        // .with(JsonParser.Feature.IGNORE_UNDEFINED)
        // .with( CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE)
    }

    private CsvSchema buildSchema(@NonNull CsvMapper mapper) {
        return mapper
                .schema()
                // .schemaFor(AbonEntry.class)   // !!! Не нужно указывать!  Схема создастся по Header-у
                .withHeader()
                .withNullValue(NULL_VALUE)
                .withColumnSeparator(DELIMITER);
    }

    @Override
    public void readFromFile(String fileName) throws IOException {
        try (Reader fileReader = Files.newBufferedReader(Paths.get(fileName), FILE_CHARSET)) {

            ObjectReader objectReader = buildObjectReader();

            MappingIterator<AbonEntry> it = objectReader.readValues(fileReader);
            while (it.hasNext()) {
                AbonEntry row = it.next();
                entryProcessor.process(row);
            }
        }
    }

    @Override
    public void saveToDB() {
        log.info("\n\n\n======= {}: Deleting old data in DB =======", getClass().getSimpleName());
        cityService.deleteAllInBatch();
        cityTypeService.deleteAllInBatch();

        log.info("\n\n\n======= {}: Saving data to DB =======", getClass().getSimpleName());
        cityTypeService.saveAll(entryProcessor.getCityTypes());
        cityService.saveAll(entryProcessor.getCities());
    }
}
