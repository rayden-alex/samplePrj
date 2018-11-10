package myProg.csv.readers;

import java.io.IOException;

public interface RecordLoader {
    void readFromFile(String fileName) throws IOException;

    void saveToDB();
}
