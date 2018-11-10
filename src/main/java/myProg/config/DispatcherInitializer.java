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

    @NonNull
    @Override
    protected Filter[] getServletFilters() {

        // Spring filter - set encoding FOR REQUEST AND RESPONSE !
        final CharacterEncodingFilter encodingFilter = new CharacterEncodingFilter();
        encodingFilter.setEncoding("UTF-8");
        encodingFilter.setForceEncoding(false);

        // converts posted method parameters into HTTP methods
        // final HiddenHttpMethodFilter httpMethodFilter = new HiddenHttpMethodFilter();

        // Tomcat provided filter:
        // https://tomcat.apache.org/tomcat-8.5-doc/config/filter.html#Set_Character_Encoding_Filter
        // https://tomcat.apache.org/tomcat-8.5-doc/config/filter.html#Add_Default_Character_Set_Filter
        // https://wiki.apache.org/tomcat/FAQ/CharacterEncoding#Q3

        // set encoding APPLIES FOR REQUEST ONLY !
        // final SetCharacterEncodingFilter characterEncodingFilter = new SetCharacterEncodingFilter();
        // characterEncodingFilter.setEncoding("UTF-8");

        // Adds a character set for text media types if no ContentType character set is specified APPLIES FOR RESPONSE ONLY !
        // final AddDefaultCharsetFilter defaultCharsetFilter = new AddDefaultCharsetFilter();
        // defaultCharsetFilter.setEncoding("UTF-8");

        return new Filter[]{encodingFilter};
    }
}
