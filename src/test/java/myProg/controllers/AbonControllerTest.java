package myProg.controllers;


import myProg.config.WebConfig;
import myProg.domain.Abon;
import myProg.services.AbonService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles({"default", "test"})
@TestPropertySource("classpath:jdbc-test.properties")

// Нужно обязательно указывать конфиги(контексты) в которых определены компоненты,
// которые будут юзатся в тесте. Если компонент будет замокан, то вроде не надо.  ??
@SpringJUnitWebConfig({WebConfig.class})

@ExtendWith(MockitoExtension.class)
class AbonControllerTest {
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    //https://stackoverflow.com/questions/39858226/springboottest-for-a-non-spring-boot-application
    @MockBean
    AbonService abonService;

    //todo private MockRestServiceServer mockRestServiceServer;

    @BeforeEach
    void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void welcome() throws Exception {
        mockMvc.perform(get("/rest/abon"))
                .andExpect(status().isOk());
    }

    @Test
    void abonFound() throws Exception {
        // https://github.com/json-path/JsonPath
        // https://github.com/spring-guides/tut-bookmarks/blob/master/rest/src/test/java/bookmarks/BookmarkRestControllerTest.java

        mockingAbonService();

        mockMvc.perform(get("/rest/abon/25")
                .accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                // .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("id", is(25)))
                .andExpect(jsonPath("@.account", is(500122)));
        //  .andExpect(content().string(containsString("Spring={};")));
    }

    private void mockingAbonService() {
        Abon mockAbon = new Abon();
        mockAbon.setId(25L);
        mockAbon.setAccount(500122);


        when(abonService.findById(25L))
                .thenReturn(Optional.of(mockAbon));

        when(abonService.findById(-100L))
                .thenReturn(Optional.empty());
    }

    @Test
    void abonNotFound() throws Exception {
        // https://github.com/json-path/JsonPath
        // https://github.com/spring-guides/tut-bookmarks/blob/master/rest/src/test/java/bookmarks/BookmarkRestControllerTest.java

        mockingAbonService();

        mockMvc.perform(get("/rest/abon/-100")
                .accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void abonEntityByIdFound() throws Exception {
        mockingAbonService();

        mockMvc.perform(get("/rest/abonById/25")
                .accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE)))
                //.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void abonEntityByIdNotFound() throws Exception {
        mockingAbonService();

        mockMvc.perform(get("/rest/abonById/-100")
                .accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE)))
                //.andDo(print())
                .andExpect(status().isBadRequest());
    }
}


