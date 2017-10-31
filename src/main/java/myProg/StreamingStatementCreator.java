package myProg;

import org.springframework.jdbc.core.PreparedStatementCreator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StreamingStatementCreator implements PreparedStatementCreator {

    private final String sql;

    private int fetchSize;

    public StreamingStatementCreator(String sql) {
        this(sql, 1000);
    }

    public StreamingStatementCreator(String sql, int fetchSize) {
        this.sql = sql;
        this.fetchSize = fetchSize;
    }

    @Override
    public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
        final PreparedStatement stmnt = conn.prepareStatement(sql,
                ResultSet.TYPE_FORWARD_ONLY,
                ResultSet.CONCUR_READ_ONLY);
        stmnt.setFetchSize(fetchSize);
        return stmnt;
    }
}
