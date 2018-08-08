package myProg.csv.readers;

import java.io.IOException;

public interface RecordReader {
    void readFromFile(String fileName) throws IOException;

    void saveToDB();
}
