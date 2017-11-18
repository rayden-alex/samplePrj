package myProg;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@Configuration
@ComponentScan("myProg")
@Import(DataBaseConfig.class)
public class AppConfig {

}
