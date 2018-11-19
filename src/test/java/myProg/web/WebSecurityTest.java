package myProg.web;

import myProg.config.WebConfig;
import myProg.config.security.SecurityConfig;
import myProg.services.AbonService;
import myProg.services.RegionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(wac)
                .apply(springSecurity())
                //.apply(sharedHttpSession())
                .build();
    }

    @Test
    void loginWithValidUserThenAuthenticated() throws Exception {
        /*
         * formLogin() creates a request (including any necessary valid CsrfToken)
         * that will submit a form based login to POST "/login".
         */
        SecurityMockMvcRequestBuilders.FormLoginRequestBuilder login = SecurityMockMvcRequestBuilders.formLogin()
                .user("user")
                .password("user");

        mockMvc.perform(login)
                //.andDo(print())
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
