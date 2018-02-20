package myProg.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@Configuration
@ComponentScan("myProg")
@ComponentScan("myProg.services")
@Import(DataBaseConfig.class)
public class AppConfig {

}
