package myProg.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.util.function.Function;

@EnableWebMvc
@Configuration
@Lazy

// WebApplicationContext (dispatcher-servlet namespace, parent: Root WebApplicationContext)
// Обязательно указываем где надо искать все относящиеся к вебу бины: контроллеры, фильтры, хендлеры и т.д.
// As such, it typically contains controllers, view resolvers, locale resolvers, and other web-related beans.
// org.springframework.web.servlet.support.AbstractDispatcherServletInitializer.createServletApplicationContext()
@ComponentScan("myProg.controllers")
public class WebConfig implements WebMvcConfigurer, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * Configures a request handler for serving static resources
     * by forwarding the request to the Servlet container's "default" Servlet.
     * This is intended to be used when the Spring MVC DispatcherServlet is mapped to "/"
     * thus overriding the Servlet container's default handling of static resources.
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * Add handlers to serve static resources such as images, js, and, css
     * files from specific locations under web application root, the classpath,
     * and others.
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("/images/");
        registry.addResourceHandler("/css/**").addResourceLocations("/css/");
        registry.addResourceHandler("/js/**").addResourceLocations("/js/");
    }


//    @Autowired
//    private RegionService regionService;
//
//    @Override
//    public void addFormatters(FormatterRegistry registry) {
//        registry.addFormatter(new RegionFormatter(regionService));
//        registry.addFormatter(new DateFormatter(messageSource()));
//    }

    /**
     * Message externalization/internationalization
     * <p>
     * org.springframework.context.MessageSource implementation
     * that accesses resource bundles using specified basenames.
     * <p>
     * <p>
     * messageSource.setDefaultEncoding();
     * <p>
     * Attention: java Property files should be encoded in ISO 8859-1!
     * <p>
     * <p>
     * ISO 8859-1 character encoding. Characters that cannot be directly represented
     * in this encoding can be written using Unicode escapes ;
     * only a single 'u' character is allowed in an escape sequence.
     * <p>
     * <p>
     * (for which IntelliJ IDEA provides the Transparent native-to-ASCII conversion option)
     */
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("Messages");

        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        var clr = new CookieLocaleResolver();
        clr.setCookieName("lang");
        return clr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(this.localeChangeInterceptor());
    }

    @Bean
    public Function<String, String> currentUrlWithoutParam() {
        return param -> ServletUriComponentsBuilder.fromCurrentRequest().replaceQueryParam(param).toUriString();
    }

    /**
     * *************************************************************** *
     * THYMELEAF-SPECIFIC ARTIFACTS                                    *
     * TemplateResolver <- TemplateEngine <- ViewResolver              *
     * *************************************************************** *
     * <p>
     * resolves templates using Spring's Resource Resolution mechanism
     * (see {@link ApplicationContext#getResource(String)}).
     */
    @Bean
    public SpringResourceTemplateResolver templateResolver() {
        // SpringResourceTemplateResolver automatically integrates with Spring's own
        // resource resolution infrastructure, which is highly recommended.
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        templateResolver.setApplicationContext(this.applicationContext);
        templateResolver.setPrefix("/WEB-INF/templates/");
        templateResolver.setSuffix(".html");
        // HTML is the default value, added here for the sake of clarity.
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        // Template cache is true by default. Set to false if you want
        // templates to be automatically updated when modified.
        templateResolver.setCacheable(true);
        return templateResolver;
    }

    /**
     * Implementation of ISpringTemplateEngine meant for Spring-enabled applications,
     * that establishes by default an instance of SpringStandardDialect as a dialect
     * (instead of an instance of org.thymeleaf.standard.StandardDialect.
     * It also configures a SpringMessageResolver as message resolver,
     * and implements the MessageSourceAware interface in order to let Spring
     * automatically setting the MessageSource used at the application
     * (bean needs to have id "messageSource").
     * If this Spring standard setting needs to be overridden,
     * the setTemplateEngineMessageSource(MessageSource) can be used.
     */
    @Bean
    public SpringTemplateEngine templateEngine() {
        // SpringTemplateEngine automatically applies SpringStandardDialect and
        // enables Spring's own MessageSource message resolution mechanisms.
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();

        templateEngine.setTemplateResolver(templateResolver());

        // Enabling the SpringEL compiler with Spring 4.2.4 or newer can
        // speed up execution in most scenarios, but might be incompatible
        // with specific cases when expressions in one template are reused
        // across different data types, so this flag is "false" by default
        // for safer backwards compatibility.
        templateEngine.setEnableSpringELCompiler(true);

        // add the SpringSecurity dialect to our Template Engine
        // so that we can use the sec:* attributes and special expression utility objects
        // https://github.com/thymeleaf/thymeleaf-extras-springsecurity
        templateEngine.addDialect(new SpringSecurityDialect());
        return templateEngine;
    }

    // Implementation of the Spring WebMVC org.springframework.web.servlet.ViewResolver interface.
    @Bean
    public ThymeleafViewResolver viewResolver() {
        ThymeleafViewResolver viewResolver = new ThymeleafViewResolver();
        viewResolver.setTemplateEngine(templateEngine());
        viewResolver.setContentType("text/html;charset=UTF-8");
        viewResolver.setCharacterEncoding("UTF-8");
        return viewResolver;
    }

    /**
     * we can register view controllers that create a direct mapping between the URL and the view name using the ViewControllerRegistry.
     * This way, there’s no need for any Controller between the two.
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
//        registry.addViewController("/logout").setViewName("logout");
        registry.addViewController("/access-denied").setViewName("error/access-denied");
        registry.addViewController("/").setViewName("regionmng");
        registry.addViewController("/secur").setViewName("secur");
        //registry.addViewController("/logout");
    }

    //    @Bean
//    public InternalResourceViewResolver getInternalResourceViewResolver() {
//        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
//        resolver.setViewClass(InternalResourceView.class);
//
////        resolver.setViewClass(JstlView.class);
//        resolver.setPrefix("/WEB-INF/jsp/");
////        resolver.setSuffix(".jsp");
//        return resolver;
//    }
}
