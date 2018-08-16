package myProg.config;

import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.DataTypeException;
import org.dbunit.dataset.datatype.DefaultDataTypeFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;

public class Firebird3DataTypeFactory extends DefaultDataTypeFactory {
    /**
     * Logger for this class
     */
    private static final Logger logger = LoggerFactory.getLogger(Firebird3DataTypeFactory.class);
    /**
     * Database product names supported.
     */
    private static final Collection DATABASE_PRODUCTS = Collections.singletonList("Firebird 3.0");

    /**
     * @see org.dbunit.dataset.datatype.IDbProductRelatable#getValidDbProducts()
     */
    @Override
    public Collection getValidDbProducts() {
        return DATABASE_PRODUCTS;
    }

    @Override
    public DataType createDataType(int sqlType, String sqlTypeName) throws DataTypeException {
        if (logger.isDebugEnabled())
            logger.debug("createDataType(sqlType={}, sqlTypeName={}) - start", String.valueOf(sqlType), sqlTypeName);

//
//        if (sqlType == Types.BIGINT) {
//            return DataType.BIGINT;
//        }
//
//        if (sqlType == Types.DATE)
//        {
//            return DataType.DATE;
//        }
//
//        // TIMESTAMP
//        if (sqlTypeName.startsWith("TIMESTAMP"))
//        {
//            return DataType.TIMESTAMP;
//        }

        return super.createDataType(sqlType, sqlTypeName);
    }
}
