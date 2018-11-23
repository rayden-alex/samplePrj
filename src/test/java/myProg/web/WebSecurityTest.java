package myProg.web;

import myProg.config.WebConfig;
import myProg.config.security.SecurityConfig;
import myProg.services.AbonService;
import myProg.services.RegionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig({WebConfig.class, SecurityConfig.class}) //, AppConfig.class, DataBaseConfig.class
@ExtendWith(MockitoExtension.class)
class WebSecurityTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @MockBean
    AbonService abonService;

    @MockBean
    RegionService regionService;

    @MockBean
    UserDetailsService userDetailsService;

    @BeforeEach
    void setup() {
        mockUserDetailService();

        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                //.apply(sharedHttpSession())
                .build();
    }

    private void mockUserDetailService() {
        final String user1 = "user";
        final String pass1 = "{bcrypt}$2a$10$ATY5KQ6vK8QI0hw0DIm2/OntJAN9ZJciCBW.6XFPgDlvHIISFjoYm";

        final String user2 = "admin";
        final String pass2 = "{bcrypt}$2a$10$LxsYfIB5z.RTlHYj1khiluilUCr756mThc90AwtQyMHpIlw..pGX.";

        UserDetails mockUserDetails1 = new User(
                user1,
                pass1,
                List.of(new SimpleGrantedAuthority("USER")));

        UserDetails mockUserDetails2 = new User(
                user2,
                pass2,
                List.of(new SimpleGrantedAuthority("USER"), new SimpleGrantedAuthority("ADMIN")));

        final Answer<UserDetails> mockedAnswer = invocation -> {
            Object argument = invocation.getArguments()[0];
            if (user1.equals(argument)) {
                return mockUserDetails1;

            } else if (user2.equals(argument)) {
                return mockUserDetails2;

            } else {
                throw new UsernameNotFoundException(String.format("User '%s' does not found", argument));
            }
        };

        when(userDetailsService.loadUserByUsername(anyString()))
                .thenAnswer(mockedAnswer);
    }

    @RepeatedTest(3)
        //  @WithMockUser(username = "user", password = "user", roles = {"USER"})
        //  @WithUserDetails("customUsername")
    void loginWithValidUserThenAuthenticated() throws Exception {
        /*
         * formLogin() creates a request (including any necessary valid CsrfToken)
         * that will submit a form based login to POST "/login".
         */
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder login = SecurityMockMvcRequestBuilders.formLogin()
                .user("user")
                .password("user");

        mockMvc.perform(login)
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(authenticated().withUsername("user"));
    }


    @Test
    void loginWithValidUser() throws Exception {
        mockMvc.perform(post("/login")
                .param("username", "user")
                .param("password", "user")
                .with(csrf()))
                //.andDo(print())
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/")); //redirect-url-on-success-login
    }

    @Test
    void loginWithInvalidUserThenUnauthenticated() throws Exception {
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder login = SecurityMockMvcRequestBuilders.formLogin()
                .user("invaliduser")
                .password("invalidpass");

        mockMvc.perform(login)
                .andExpect(unauthenticated());
    }

    @Test
    void loginWithInvalidUser() throws Exception {
        mockMvc.perform(post("/login")
                .param("username", "invaliduser")
                .param("password", "invalidpass")
                .with(csrf()))
                //.andDo(print())
                .andExpect(status().isFound())
                .andExpect(header().string("Location", "/login?error")); //redirect-url-on-unsuccessful-login
    }

    @Test
    void accessUnsecuredResourceThenOk() throws Exception {
        mockMvc.perform(get("/rest/abon"))
                .andExpect(status().isOk());
    }

    @Test
    void accessSecuredResourceUnauthenticatedThenRedirectsToLogin() throws Exception {
        mockMvc.perform(get("/regionmng"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    //https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#test-method-withmockuser
    @WithMockUser(username = "user1", password = "user1", roles = {"USER1", "ADMIN"})
    void accessSecuredResourceAuthenticatedThenOk() throws Exception {
        mockMvc.perform(get("/regionmng"))
                .andExpect(authenticated().withUsername("user1").withRoles("USER1", "ADMIN"))
                .andExpect(status().isOk());
    }

    @Test
    void logoutAuthenticatedUserThenUnauthenticated() throws Exception {
        /*
         * logout() creates a logout POST request (including any necessary valid CsrfToken)
         * to the "/logout" URL
         */
        SecurityMockMvcRequestBuilders.LogoutRequestBuilder logout = SecurityMockMvcRequestBuilders.logout();

        mockMvc.perform(logout)
                //.andDo(print())
                .andExpect(unauthenticated())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?logout"))
                .andExpect(cookie().value("JSESSIONID", (String) null));

    }
}
