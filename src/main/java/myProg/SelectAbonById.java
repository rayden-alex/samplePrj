package myProg;

import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.object.MappingSqlQuery;
import org.springframework.lang.Nullable;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

public class SelectAbonById extends MappingSqlQuery<Abon> {
    private static final String SQL_FIND_BY_ID =
            "select ID, FIO, PHONE_LOCAL from ABON where ID = :ID";

    public SelectAbonById(DataSource dataSource) {
        super(dataSource, SQL_FIND_BY_ID);
        super.declareParameter(new SqlParameter("ID", Types.BIGINT));
    }

    @Nullable
    @Override
    protected Abon mapRow(ResultSet rs, int rowNum) throws SQLException {
        Abon abon = new Abon();

        abon.setId(rs.getLong("ID"));
        abon.setFio(rs.getString("FIO"));
        abon.setPhone(rs.getString("PHONE_LOCAL"));

        return abon;
    }
}
