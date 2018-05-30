package myProg.config;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.lang.NonNull;


@Configuration

// WebApplicationContext (Root namespace)
// Здесь указываем где искать прочие бины (не относящиеся к вебу). Хотя можно сканить вообще все - но долго.
// Т.е. для структурирования web-бины настраиваем в одном конфиге (WebConfig - через этот конфиг затем создается ServletContext),
// а остальные AppConfig.
// As such, it typically contains middle-tier services, data sources, etc.
// org.springframework.web.context.AbstractContextLoaderInitializer.createRootApplicationContext()
//
// Все равно потом все бины собираются в

@ComponentScan("myProg.services")
@ComponentScan("myProg.jpa")
@ComponentScan("myProg.dao")
@ComponentScan("myProg.csv")
@Import(DataBaseConfig.class)
public class AppConfig {

    @NonNull
    @Bean
    public CsvMapper csvFileMapper() {
        return (CsvMapper) new CsvMapper().registerModule(new JavaTimeModule()); // new module, NOT JSR310Module

    }
}
