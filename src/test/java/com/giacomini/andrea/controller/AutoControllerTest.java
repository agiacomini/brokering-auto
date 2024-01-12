package com.giacomini.andrea.controller;

import com.giacomini.andrea.Application;
import org.json.JSONException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ContextConfiguration(classes = Application.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AutoControllerTest {

    private MockMvc mvc;

    @Autowired
    WebApplicationContext wac;

    @BeforeEach
    public void setup() throws JSONException, IOException {
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @Order(1)
    public void testSelectAutoById() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/v1/auto/select/244")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                // AUTO
                .andExpect(jsonPath("$.quattroPerQuattro").exists())
                .andExpect(jsonPath("$.quattroPerQuattro").value(false))
                .andExpect(jsonPath("$.fasciaDiPrezzo").exists())
                .andExpect(jsonPath("$.fasciaDiPrezzo").value("0 a 5K"))
                .andExpect(jsonPath("$.anzianita").exists())
                .andExpect(jsonPath("$.anzianita").value(1))
                .andExpect(jsonPath("$.cambioAutomatico").exists())
                .andExpect(jsonPath("$.cambioAutomatico").value(true))
                .andExpect(jsonPath("$.alimentazione").exists())
                .andExpect(jsonPath("$.alimentazione").value("benzina"))

                .andDo(print());
    }
}