package myProg;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("myProg")
@Import(DataSourceConfig.class)
public class AppConfig {
    private BasicDataSource dataSource;

    @Bean("abonDaoBean")
    public AbonDao abonDao() {
        AbonDaoImpl dao = new AbonDaoImpl();
        dao.setDataSource(dataSource);
        return dao;
    }

    @Autowired
    @Qualifier("dataSource")
    private void setDataSource(BasicDataSource dataSource) {
        this.dataSource = dataSource;
    }
}
