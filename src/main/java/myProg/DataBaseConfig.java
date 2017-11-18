package myProg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

@Configuration
@EnableTransactionManagement()
@PropertySource("classpath:META-INF/config/jdbc.properties")
public class DataBaseConfig {

    @Value("${jdbc.driverClassName}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;


    @Bean(name = "namedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Profile("dev")
    @Bean(destroyMethod = "close")
    @SuppressWarnings("Duplicates")
    public org.apache.tomcat.jdbc.pool.DataSource devDataSource() {

        org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
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

    @Profile("test")
    @Bean(destroyMethod = "close")
    @SuppressWarnings("Duplicates")
    public org.apache.tomcat.jdbc.pool.DataSource testDataSource() {

        org.apache.tomcat.jdbc.pool.DataSource ds = new org.apache.tomcat.jdbc.pool.DataSource();
        ds.setDriverClassName(driverClassName);
        ds.setUrl(url);
        ds.setUsername(username);
        ds.setPassword(password);
        ds.setConnectionProperties("encoding=UTF8");
        ds.setDefaultTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);

        ds.setDefaultAutoCommit(false);
        ds.setRollbackOnReturn(true);

        return ds;
    }


    @Bean DataSource dataSource(DataSource ds){
        return ds;
    }

    @Bean
    //It’s important to create LocalContainerEntityManagerFactoryBean and not EntityManagerFactory directly
    //since the former also participates in exception translation mechanisms besides simply creating EntityManagerFactory
    //т.е. бин EntityManagerFactory сформирует сам Spring (его можно будет заинжектить через @PersistenceUnit)
    //точно так же бин EntityManager тоже сформирует сам Spring (его можно будет заинжектить через @PersistenceContext)

    //The truth is that @PersistenceContext doesn’t inject an EntityManager—at least, not exactly.
    //Instead of giving the repository a real EntityManager, it gives a proxy to a
    //real EntityManager. That real EntityManager either is one associated with the cur-
    //rent transaction or, if one doesn’t exist, creates a new one. Thus, you know that you’re
    //always working with an entity manager in a thread-safe way.
    public LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean(DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean lc = new LocalContainerEntityManagerFactoryBean();
        lc.setDataSource(dataSource);
        lc.setPersistenceUnitName("FirebirdPersistenceUnit");
        lc.setJpaVendorAdapter(new HibernateJpaVendorAdapter()); //  ???     META-INF/persistence.xml:5      hibernate.properties:1
        //lc.setJpaDialect( /* JpaDialect установится при вызове setJpaVendorAdapter */);
        //lc.setPackagesToScan();


        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.show_sql", "true");
        jpaProperties.setProperty("hibernate.format_sql", "true");
        jpaProperties.setProperty("hibernate.use_sql_comments", "true");
        jpaProperties.setProperty("hibernate.hbm2ddl.auto", "none");
        jpaProperties.setProperty("hibernate.generateDdl", "false");
        jpaProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.FirebirdDialect");

        lc.setJpaProperties(jpaProperties);

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
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);  //  ????
    }
}
