package myProg;

import lombok.extern.slf4j.Slf4j;
import myProg.config.AppConfig;
import myProg.services.AbonService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


@Slf4j
public class Program {
    public static void main(String[] args) {

        ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        log.info("ApplicationContext is UP");

        AbonService abonService = ctx.getBean("abonServiceImpl", AbonService.class);

        log.info(abonService.count().toString());
    }
}
