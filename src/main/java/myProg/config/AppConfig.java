package myProg.config;

import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@ComponentScan("myProg")
@ComponentScan("myProg.services")
@Import(DataBaseConfig.class)
public class AppConfig {

    @NonNull
    @Bean
    public CsvMapper csvFileMapper() {
        return (CsvMapper) new CsvMapper().registerModule(new JavaTimeModule()); // new module, NOT JSR310Module
    }
}
