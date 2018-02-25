package myProg.csv;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class AbonReader {

    private static final String NULL_VALUE = "(null)";
    private static final char DELIMITER = ';';
    private static final Charset FILE_CHARSET = Charset.forName("windows-1251");


    private CsvMapper mapper;
    private CsvSchema schema;

    @Autowired
    AbonReader(@NonNull CsvMapper mapper) {
        this.mapper = mapper;
        schema = mapper
                .schema()
                // .schemaFor(AbonEntry.class)   // !!! Не нужно указывать!  Схема создастся по Header-у
                .withHeader()
                .withNullValue(NULL_VALUE)
                .withColumnSeparator(DELIMITER);
    }

    public List<AbonEntry> readFromFile(String fileName) throws IOException {
        try (Reader fileReader = Files.newBufferedReader(Paths.get(fileName), FILE_CHARSET)) {

            MappingIterator<AbonEntry> it = mapper
                    .readerFor(AbonEntry.class)
                    .with(schema)
                    .withFeatures(CsvParser.Feature.TRIM_SPACES)
                    .withoutFeatures(JsonParser.Feature.AUTO_CLOSE_SOURCE)
                    // .with(JsonParser.Feature.IGNORE_UNDEFINED)
                    // .with( CsvParser.Feature.IGNORE_TRAILING_UNMAPPABLE)
                    .readValues(fileReader);

            //        while (it.hasNext()) {
//            AbonEntry row = it.next();
//            System.out.println(row);
//        }

            return it.readAll();
        }
    }
}
