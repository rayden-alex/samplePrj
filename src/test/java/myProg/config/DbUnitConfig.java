package myProg.config;

import com.github.springtestdbunit.bean.DatabaseConfigBean;
import com.github.springtestdbunit.bean.DatabaseDataSourceConnectionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;


@Profile("test")
@Configuration
@Import(DataBaseConfig.class) // needed for autowire the "dataSource"
public class DbUnitConfig {
    @Autowired
    DataSource dataSource;

    // Bean name to reference in your @DbUnitConfiguration(databaseConnection="...")
    @Bean(name = "dbUnitDatabaseConnection")
    public DatabaseDataSourceConnectionFactoryBean getDatabaseDataSourceConnectionFactoryBean() {
        DatabaseConfigBean configBean = new DatabaseConfigBean();
        configBean.setFetchSize(100);
        configBean.setDatatypeFactory(new Firebird3DataTypeFactory());

        DatabaseDataSourceConnectionFactoryBean connectionFactoryBean = new DatabaseDataSourceConnectionFactoryBean();
        connectionFactoryBean.setDatabaseConfig(configBean);
        connectionFactoryBean.setDataSource(dataSource);
        // connectionFactoryBean.setSchema("my_schema");
        return connectionFactoryBean;
    }
}
