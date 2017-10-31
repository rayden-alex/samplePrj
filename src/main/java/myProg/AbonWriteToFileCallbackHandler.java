package myProg;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.lang.NonNull;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AbonWriteToFileCallbackHandler implements RowCallbackHandler, AutoCloseable {

    private BufferedWriter writer;

    public void open() {
        try {
            writer = Files.newBufferedWriter(Paths.get("D:/temp/numbers2.txt"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processRow(@NonNull ResultSet rs) throws SQLException {
        if (writer == null) {
            open();
        }

        try {
            Abon abon = new Abon();

            abon.setId(rs.getLong("ID"));
            abon.setFio(rs.getString("FIO"));
            abon.setPhone(rs.getString("PHONE_LOCAL"));

            writer.write(abon.toString());
            writer.newLine();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() throws IOException {
        if (writer != null) {
            writer.close();
        }
    }
}
