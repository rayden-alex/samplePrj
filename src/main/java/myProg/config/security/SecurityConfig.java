package myProg.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    public void configure(WebSecurity web) throws Exception {
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
        http.authorizeRequests()
                .antMatchers("/rest/**").permitAll()
                .antMatchers("/**").access("hasRole('USER')")
                .antMatchers("/confidential/**").access("hasRole('ADMIN')")
                .antMatchers("/login*").permitAll()
                //.anyRequest().authenticated()
                //.and().formLogin().defaultSuccessUrl("/", false)

                .and().formLogin().loginPage("/login.html").loginProcessingUrl("/perform_login").permitAll()
                .and().logout().deleteCookies("JSESSIONID").permitAll();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("{noop}user").roles("USER");
        auth.inMemoryAuthentication().withUser("admin").password("{noop}admin").roles("ADMIN");
    }
}
