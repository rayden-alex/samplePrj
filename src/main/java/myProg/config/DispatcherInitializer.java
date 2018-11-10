package myProg.config;

import org.springframework.lang.NonNull;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.Filter;

public class DispatcherInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        //"root" application context (non-web infrastructure) configuration
        return new Class<?>[]{
                AppConfig.class,
                DataBaseConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        //DispatcherServlet application context (Spring MVC infrastructure) configuration
        return new Class<?>[]{WebConfig.class};
    }

    @NonNull
    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

}
