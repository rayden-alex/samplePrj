package myProg;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("myProg")
@Import(DataSourceConfig.class)
public class AppConfig {

}
