package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.entities.Specialty;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class SpecialtyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFindAllSpecialties() throws Exception {
        this.mockMvc.perform(get("/specialties"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].name", hasItem("radiology")));
    }

    @Test
    public void testCreateSpecialty() throws Exception {
        Specialty newSpecialty = new Specialty();
        newSpecialty.setName("Cardiology");

        this.mockMvc.perform(post("/specialties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newSpecialty)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Cardiology")));
    }

    @Test
    public void testDeleteSpecialty() throws Exception {
        Specialty newSpecialty = new Specialty();
        newSpecialty.setName("ToDelete");

        ResultActions result = this.mockMvc.perform(post("/specialties")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newSpecialty)));

        String response = result.andReturn().getResponse().getContentAsString();
        Integer specialtyId = com.jayway.jsonpath.JsonPath.read(response, "$.id");

        this.mockMvc.perform(delete("/specialties/" + specialtyId))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/specialties/" + specialtyId))
                .andExpect(status().isNotFound());
    }
}
