package myProg.config.security;

// Почему-то не работает, если создать этот класс
// Бин springSecurityFilterChain регистрируется дважды.
// Если же добавить SecurityConfig.class в myProg.config.DispatcherInitializer.getRootConfigClasses,
// то все норм.
public class SecurityWebInitializer {//extends AbstractSecurityWebApplicationInitializer {
//    AbstractSecurityWebApplicationInitializer implements WebApplication-
//    Initializer, so it will be discovered by Spring and be used to register Delegating-
//    FilterProxy with the web container, it will intercept requests coming
//    into the application and delegate them to a bean whose ID is "springSecurityFilterChain".


//    public SecurityWebInitializer() {
//        super(SecurityConfig.class);
//    }
}
