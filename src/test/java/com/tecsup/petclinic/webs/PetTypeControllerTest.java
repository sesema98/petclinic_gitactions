package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.entities.PetType;
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
public class PetTypeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFindAllPetTypes() throws Exception {
        this.mockMvc.perform(get("/pettypes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[*].name", hasItem("cat")));
    }

    @Test
    public void testCreatePetType() throws Exception {
        PetType newPetType = new PetType();
        newPetType.setName("Fish");

        this.mockMvc.perform(post("/pettypes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newPetType)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Fish")));
    }

    @Test
    public void testDeletePetType() throws Exception {
        PetType newPetType = new PetType();
        newPetType.setName("ToDelete");

        ResultActions result = this.mockMvc.perform(post("/pettypes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newPetType)));

        String response = result.andReturn().getResponse().getContentAsString();
        Integer petTypeId = com.jayway.jsonpath.JsonPath.read(response, "$.id");

        this.mockMvc.perform(delete("/pettypes/" + petTypeId))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/pettypes/" + petTypeId))
                .andExpect(status().isNotFound());
    }
}
