package myProg.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.Connection;
import java.util.Properties;

@Slf4j
@Configuration
@EnableTransactionManagement()
@PropertySource("classpath:jdbc.properties") //${my.placeholder:default/path}
public class DataBaseConfig {

    @Value("${jdbc.driverClassName}")
    private String driverClassName;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Value("${jdbc.validationQuery}")
    private String validationQuery;

    @Value("${jdbc.validationInterval}")
    private long validationInterval;

    @Value("${jndi.DB.url}")
    private String jndiDbUrl;


    @Bean//(name = "namedParameterJdbcTemplate")
    public NamedParameterJdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Profile("prod")
    // через Run configuration / Startup/ Pass Environment variables / spring.profiles.active=prod  -- НЕ работает!
    // через Run configuration / VM Options /  -Dspring.profiles.active=prod   -- работает

    // через application web.xml или через d:\Tomcat\conf\web.xml
    //  <context-param>
    //    <param-name>spring.profiles.active</param-name>
    //    <param-value>prod</param-value>
    //  </context-param>   ---- работает.   ВЫБРАН ЭТОТ СПОСОБ!
    @Bean(name = "dataSource")//, destroyMethod = "close")
    public DataSource jndiDataSource() {
        DataSource ds = new JndiDataSourceLookup().getDataSource(jndiDbUrl);
        log.info("==================JNDI DataSource params===============----------");
        log.info(ds.toString());

        return ds;
    }

    @NoProfilesEnabled({"prod", "test"})
    @Bean(name = "dataSource", destroyMethod = "close")
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
        ds.setValidationQuery(validationQuery);
        ds.setValidationInterval(validationInterval);

        log.info("==================dev DataSource params===============----------");
        log.info(ds.toString());

        return ds;
    }

    @Profile("test")
    @Bean(name = "dataSource", destroyMethod = "close")
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
        ds.setValidationQuery(validationQuery);
        ds.setValidationInterval(validationInterval);

        log.info("==================test DataSource params===============----------");
        log.info(ds.toString());

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
        LocalContainerEntityManagerFactoryBean emfb = new LocalContainerEntityManagerFactoryBean();
        emfb.setDataSource(dataSource);
        emfb.setJpaVendorAdapter(new HibernateJpaVendorAdapter()); //  ???     META-INF/__p_rsistence.xml:5      hibernate.properties:1
        //emfb.setJpaDialect( /* JpaDialect установится при вызове setJpaVendorAdapter */);

        //   Such package scanning defines a "default persistence unit" in Spring, which
        //	 * may live next to regularly defined units originating from persistence.xml
        //	 * Its name is determined by #setDefaultPersistenceUnitName: by default,
        //	 * it's simply "default".
        //   Т.е. при скане создается "default" PersistenceUnit и задавать другое имя нельзя!
        //   (ну или если создать отдельные PersistenceUnitManager )
        //   https://stackoverflow.com/questions/21180785/property-packagestoscan-not-working
        emfb.setPackagesToScan("myProg.jpa.entity"); // Работает только в конфигурации БЕЗ!!! persistence.xml, т.е. его вообще не должно быть, даже пустого.

        //  emfb.setPersistenceUnitName("FirebirdPersistenceUnit"); // Нужно только при наличии нескольких PersistenceUnit


        Properties jpaProperties = new Properties();
        jpaProperties.setProperty("hibernate.show_sql", "true");
        jpaProperties.setProperty("hibernate.format_sql", "true");
        jpaProperties.setProperty("hibernate.use_sql_comments", "true");

        jpaProperties.setProperty("hibernate.hbm2ddl.auto", "none");
        jpaProperties.setProperty("hibernate.generateDdl", "false");
        jpaProperties.setProperty("hibernate.dialect", "myProg.config.Firebird3CustomDialect");
        jpaProperties.setProperty("hibernate.jdbc.fetch_size", "100");

        emfb.setJpaProperties(jpaProperties);

        return emfb;
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
