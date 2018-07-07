package myProg.controllers;


import myProg.config.AppConfig;
import myProg.config.WebConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringJUnitWebConfig({AppConfig.class, WebConfig.class}) // Нужно обязательно указывать и root-context, и servlet-context!
@ActiveProfiles({"default", "test"})
class AbonControllerTest {
    @Test
    void welcome() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void abonFound() throws Exception {
        // https://github.com/json-path/JsonPath
        // https://github.com/spring-guides/tut-bookmarks/blob/master/rest/src/test/java/bookmarks/BookmarkRestControllerTest.java


        this.mockMvc.perform(get("/abon/25")
                .accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE)))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                // .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("id", is(25)))
                .andExpect(jsonPath("@.account", is(500122)));
        //  .andExpect(content().string(containsString("Spring={};")));
    }

    @Test
    void abonNotFound() throws Exception {
        // https://github.com/json-path/JsonPath
        // https://github.com/spring-guides/tut-bookmarks/blob/master/rest/src/test/java/bookmarks/BookmarkRestControllerTest.java


        this.mockMvc.perform(get("/abon/-100")
                .accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE)))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void abonEntityByIdFound() throws Exception {
        this.mockMvc.perform(get("/abonById/25")
                .accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE)))
                //.andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void abonEntityByIdNotFound() throws Exception {
        this.mockMvc.perform(get("/abonById/-100")
                .accept(MediaType.parseMediaType(MediaType.APPLICATION_JSON_UTF8_VALUE)))
                //.andDo(print())
                .andExpect(status().isBadRequest());
    }
}

