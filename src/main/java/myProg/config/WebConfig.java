package myProg.config;

import org.springframework.boot.actuate.autoconfigure.EndpointAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.EndpointWebMvcAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.HealthIndicatorAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.ManagementServerPropertiesAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@EnableWebMvc
@Configuration

// WebApplicationContext (dispatcher-servlet namespace, parent: Root WebApplicationContext)
// Обязательно указываем где надо искать все относящиеся к вебу бины: контроллеры, фильтры, хендлеры и т.д.
// As such, it typically contains controllers, view resolvers, locale resolvers, and other web-related beans.
// org.springframework.web.servlet.support.AbstractDispatcherServletInitializer.createServletApplicationContext()
@ComponentScan("myProg.controllers")
@Import({EndpointWebMvcAutoConfiguration.class,
        ManagementServerPropertiesAutoConfiguration.class, EndpointAutoConfiguration.class,
        HealthIndicatorAutoConfiguration.class})
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        // Configures a request handler for serving static resources
        // by forwarding the request to the Servlet container's "default" Servlet.
        // This is intended to be used when the Spring MVC DispatcherServlet is mapped to "/"
        // thus overriding the Servlet container's default handling of static resources.
        configurer.enable();
    }


    @Bean
    public InternalResourceViewResolver getInternalResourceViewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setViewClass(InternalResourceView.class);

//        resolver.setViewClass(JstlView.class);
        resolver.setPrefix("/WEB-INF/jsp/");
//        resolver.setSuffix(".jsp");
        return resolver;
    }
}
