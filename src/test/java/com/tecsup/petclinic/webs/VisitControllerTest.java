package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.entities.Visit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class VisitControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFindAllVisits() throws Exception {
        this.mockMvc.perform(get("/visits"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].description", hasItem("rabies shot")));
    }

    @Test
    public void testCreateVisit() throws Exception {
        Visit newVisit = new Visit();
        newVisit.setVisitDate(LocalDate.of(2024, 11, 11));
        newVisit.setDescription("Regular checkup");
        Pet pet = new Pet();
        pet.setId(1);
        newVisit.setPet(pet);

        this.mockMvc.perform(post("/visits")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newVisit)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.description", is("Regular checkup")));
    }

    @Test
    public void testDeleteVisit() throws Exception {
        Visit newVisit = new Visit();
        newVisit.setVisitDate(LocalDate.of(2024, 11, 11));
        newVisit.setDescription("To be deleted");
        Pet pet = new Pet();
        pet.setId(1);
        newVisit.setPet(pet);

        ResultActions result = this.mockMvc.perform(post("/visits")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newVisit)));

        String response = result.andReturn().getResponse().getContentAsString();
        Integer visitId = com.jayway.jsonpath.JsonPath.read(response, "$.id");

        this.mockMvc.perform(delete("/visits/" + visitId))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/visits/" + visitId))
                .andExpect(status().isNotFound());
    }
}
