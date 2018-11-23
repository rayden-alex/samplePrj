package myProg.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.cache.SpringCacheBasedUserCache;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    @Autowired
    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring()
                // Spring Security should completely ignore URLs starting with /resources/
                .antMatchers("/css/**", "/images/**");
    }

    @Override
    /**
     * Also for LoginPage we need map a view controller to the login URL path
     * @see myProg.config.WebConfig#addViewControllers
     */
    protected void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http
            .authorizeRequests()
                .antMatchers("/rest/**").permitAll()
                .antMatchers("/login*").permitAll()
                //.antMatchers("/**").access("hasRole('USER')")
                //.antMatchers("/confidential/**").access("hasRole('ADMIN')")
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                 // .loginProcessingUrl("/perform_login")
                .defaultSuccessUrl("/", false)
                .permitAll()
                .and()
            .logout()
                .logoutUrl("/logout")
                .invalidateHttpSession(true)
                .clearAuthentication(true)
                .deleteCookies("JSESSIONID")
                .permitAll();
        // @formatter:on
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public AuthenticationProvider customAuthenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userDetailsService);
        auth.setPasswordEncoder(passwordEncoder());

        // Authentication cache

        // Caching is handled by storing the UserDetails object being placed in the UserCache.
        // This ensures that subsequent requests with the same username
        // can be validated without needing to query the UserDetailsService.
        // It should be noted that if a user appears to present an incorrect password,
        // the UserDetailsService will be queried to confirm the most up-to-date password
        // was used for comparison.

        // Caching is only likely to be required for stateless applications.
        // In a normal web application, for example, the SecurityContext is stored in the user's session
        // and the user isn't reauthenticated on each request.
        // The default cache implementation is therefore NullUserCache.
        auth.setUserCache(userCache());
        return auth;
    }

    @Bean
    public UserCache userCache() {
        // Implemented cache for educational purposes only.!!!!!!!!!!

        // ConcurrentMapCache(and SpringCacheBasedUserCache accordingly) doesn't support
        // caching time-to-live, force removal and other params.
        // On production more advanced cache is necessary (like EhCache)
        /**{@link org.springframework.cache.jcache.JCacheCacheManager},
         * {@link org.springframework.cache.ehcache.EhCacheCacheManager},
         * {@link org.springframework.cache.caffeine.CaffeineCacheManager}.
         */
        // Caching is generally only required in applications which do not maintain server-side
        // state, such as remote clients or web services. The authentication credentials are then
        // presented on each invocation and the overhead of accessing a database or other
        // persistent storage mechanism to validate would be excessive. In this case, you would
        // configure a cache to store the <tt>UserDetails</tt> information rather than loading it
        // each time.

        ConcurrentMapCache cacheStorage = new ConcurrentMapCache("customUserDetailsCache");

        try {
            UserCache userCache = new SpringCacheBasedUserCache(cacheStorage);
            return new CopyBasedUserCacheDecorator(userCache);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(customAuthenticationProvider());

        // https://docs.spring.io/spring-security/site/docs/current/reference/html5/#nsa-authentication
        // https://docs.spring.io/spring-security/site/docs/current/reference/html5/#core-services-authentication-manager

        // Erasing Credentials on Successful Authentication
        // By default (from Spring Security 3.1 onwards) the ProviderManager will attempt to clear
        // any sensitive credentials information from the Authentication object
        // which is returned by a successful authentication request.
        // This prevents information like passwords being retained longer than necessary.

        // This may cause issues when you are using a cache of user objects,
        // for example, to improve performance in a stateless application.
        // If the Authentication contains a reference to an object in the cache (such as a UserDetails instance)
        // and this has its credentials removed, then it will no longer be possible to authenticate against the cached value.
        // You need to take this into account if you are using a cache.
        // An obvious solution is to make a copy of the object first, either in the cache implementation
        // or in the AuthenticationProvider which creates the returned Authentication object.
        // Alternatively, you can disable the eraseCredentialsAfterAuthentication property on ProviderManager

        //auth.eraseCredentials(false);
    }
}
