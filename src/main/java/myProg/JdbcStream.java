package myProg;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.rowset.ResultSetWrappingSqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class JdbcStream {

    private class StreamableResultSetExtractor<T> implements ResultSetExtractor<T> {
        private Function<Stream<SqlRowSet>, ? extends T> streamer;

        public StreamableResultSetExtractor(Function<Stream<SqlRowSet>, ? extends T> streamer) {
            this.streamer = streamer;
        }

        @Nullable
        @Override
        public T extractData(@Nullable ResultSet rs) throws SQLException, DataAccessException {
            final SqlRowSet rowSet = new ResultSetWrappingSqlRowSet(Objects.requireNonNull(rs));
            final boolean PARALLEL = false;

            Spliterator<SqlRowSet> spliterator;

            // The ResultSet API has a slight impedance mismatch with Iterators, so this conditional
            // simply returns an empty iterator if there are no results
            if (rowSet.next()) {
                spliterator = Spliterators.spliteratorUnknownSize(createIterator(rowSet), Spliterator.IMMUTABLE);
            } else {
                spliterator = Spliterators.emptySpliterator();
            }

            return streamer.apply(StreamSupport.stream(spliterator, PARALLEL));
        }

    }

    private Iterator<SqlRowSet> createIterator(SqlRowSet rowSet) {
        return new Iterator<SqlRowSet>() {
            private boolean isMoved = true; // is cursor in ResultSet is moved.     rowSet.next() - move cursor

            @Override
            public boolean hasNext() {
                if (isMoved) {
                    return !rowSet.isLast();
                } else {
                    isMoved = true;
                    return rowSet.next();
                }
            }

            @Override
            public SqlRowSet next() {
                if (!isMoved && !rowSet.next()) {
                    throw new NoSuchElementException();
                }

                isMoved = false;
                return rowSet;
            }
        };
    }

    private NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcStream(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    public <T> T streamQuery(String sql, SqlParameterSource paramSource, Function<Stream<SqlRowSet>, ? extends T> streamer) {
        return jdbcTemplate.query(sql, paramSource, new StreamableResultSetExtractor<T>(streamer));
    }
}
