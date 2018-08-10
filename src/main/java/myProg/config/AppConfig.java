package myProg.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.env.Environment;
import org.springframework.lang.NonNull;

import java.util.Arrays;

@Slf4j
@Configuration
@Lazy

// WebApplicationContext (Root namespace)
// Здесь указываем где искать прочие бины (не относящиеся к вебу). Хотя можно сканить вообще все - но долго.
// Т.е. для структурирования web-бины настраиваем в одном конфиге (WebConfig - через этот конфиг затем создается ServletContext),
// а остальные AppConfig.
// As such, it typically contains middle-tier services, data sources, etc.
// org.springframework.web.context.AbstractContextLoaderInitializer.createRootApplicationContext()
//
// Все равно потом все бины собираются в

@ComponentScan("myProg.services")
@Import({
        DataBaseConfig.class,
        CsvConfig.class
        /*, SecurityConfig.class**/})
public class AppConfig implements EnvironmentAware {

    static {
        System.setProperty("management.security.enabled", "false");
    }


    @Override
    public void setEnvironment(@NonNull Environment environment) {
        log.info("==================Active Profiles===============----------");
        log.info("ActiveProfiles: {}", Arrays.toString(environment.getActiveProfiles()));
    }
}
