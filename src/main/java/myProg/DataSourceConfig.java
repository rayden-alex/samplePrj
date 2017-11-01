package myProg;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.sql.Connection;

@Configuration
@EnableTransactionManagement()
@PropertySource("classpath:META-INF/config/jdbc.properties")
public class DataSourceConfig {

    @Value("${jdbc.driverClassName}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;


    @Bean(name = "namedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate jdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    @Bean(name = "dataSource", destroyMethod = "close")
    public DataSource dataSource() {

        DataSource ds = new DataSource();
        ds.setDriverClassName(driverClassName);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setConnectionProperties("encoding=UTF8");
        ds.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

//10.1 Result sets
//
//Jaybird behaves differently not only when different result set types are used but also whether the connection is in auto-commit mode or not.
//
//ResultSet.TYPE_FORWARD_ONLY result sets when used in auto-commit mode are completely cached on the client before the execution of the query is finished. This leads to the increased time needed to execute statement, however the result set navigation happens almost instantly.
// When auto-commit mode is switched off, only part of the result set specified by the fetch size is cached on the client.
//ResultSet.TYPE_SCROLL_INSENSITIVE result sets are always cached on the client. The reason is quite simple – the Firebird API does not (yet) provide scrollable cursor support, navigation is possible only in one direction.
//ResultSet.HOLD_CURSORS_OVER_COMMIT holdability is supported in Jaybird only for result sets of type ResultSet.TYPE_SCROLL_INSENSITIVE. For other result set types driver will throw an exception.

        ds.setDefaultAutoCommit(false);
        ds.setRollbackOnReturn(true);

        return ds;
    }

    @Bean(name = "entityManagerFactoryBean")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean() {
        LocalContainerEntityManagerFactoryBean lc = new LocalContainerEntityManagerFactoryBean();
        lc.setDataSource(dataSource());
        lc.setPersistenceUnitName("FirebirdPersistenceUnit");
      //  lc.setJpaVendorAdapter(new HibernateJpaVendorAdapter()); //  ???     META-INF/persistence.xml:5
        return lc;
    }

    /**
     * Менеджер транзакций
     */
//    @Bean
//    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
//        HibernateTransactionManager htm = new HibernateTransactionManager();
//        htm.setSessionFactory(sessionFactory);
//        return htm;
//    }

    @Bean
    public PlatformTransactionManager txManager() {
        return new JpaTransactionManager(entityManagerFactoryBean().getObject());  //  ????
    }
}
