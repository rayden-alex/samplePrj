package myProg.config;

import org.hibernate.dialect.FirebirdDialect;
import org.hibernate.dialect.function.NoArgSQLFunction;
import org.hibernate.dialect.function.NvlFunction;
import org.hibernate.dialect.function.StandardSQLFunction;
import org.hibernate.type.StandardBasicTypes;

import java.sql.Types;
import java.util.Locale;

public class Firebird3CustomDialect extends FirebirdDialect {

    public Firebird3CustomDialect() {
        super();

        registerColumnType(Types.BIGINT, "bigint");

        registerFunction("coalesce", new NvlFunction());
        registerFunction("current_timestamp", new NoArgSQLFunction("current_timestamp", StandardBasicTypes.TIMESTAMP, false));
        registerFunction("char_length", new StandardSQLFunction("char_length", StandardBasicTypes.INTEGER));
        registerFunction("bit_length", new StandardSQLFunction("bit_length", StandardBasicTypes.INTEGER));
        registerFunction("octet_length", new StandardSQLFunction("octet_length", StandardBasicTypes.INTEGER));
    }

    @Override
    public boolean supportsCurrentTimestampSelection() {
        return true;
    }

    @Override
    public boolean isCurrentTimestampSelectStringCallable() {
        return false;
    }

    /**
     * Retrieve the command used to retrieve the current timestamp from the
     * database.
     *
     * @return The command.
     */
    @Override
    public String getCurrentTimestampSelectString() {
        return "select CURRENT_TIMESTAMP from RDB$DATABASE";
    }

    /**
     * Does this dialect support sequences?
     *
     * @return True if sequences supported; false otherwise.
     */
    @Override
    public boolean supportsSequences() {
        return true;
    }

    @Override
    public boolean supportsPooledSequences() {
        return false;
    }

    /**
     * Generate the appropriate select statement to to retrieve the next value
     * of a sequence.
     * <p/>
     * This should be a "stand alone" select statement.
     *
     * @param sequenceName the name of the sequence
     * @return String The "nextval" select string.
     */
    @Override
    public String getSequenceNextValString(String sequenceName) {
        return "select " + getSelectSequenceNextValString(sequenceName) + " from RDB$DATABASE";
    }

    @Override
    public String getSelectSequenceNextValString(String sequenceName) {
        return "next value for " + sequenceName;
    }

    @Override
    public String getCreateSequenceString(String sequenceName) {
        return "create SEQUENCE " + sequenceName;
    }

    @Override
    public String getDropSequenceString(String sequenceName) {
        return "DROP SEQUENCE " + sequenceName.toUpperCase(Locale.ROOT) + "'";
    }

    @Override
    public String getQuerySequencesString() {
        return "select RDB$GENERATOR_NAME from RDB$GENERATORS where RDB$SYSTEM_FLAG = 0";
    }

    /**
     * Does this dialect support UNION ALL, which is generally a faster
     * variant of UNION?
     *
     * @return True if UNION ALL is supported; false otherwise.
     */
    @Override
    public boolean supportsUnionAll() {
        return true;
    }

}
