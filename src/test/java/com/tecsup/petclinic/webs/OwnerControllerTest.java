package com.tecsup.petclinic.webs;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tecsup.petclinic.entities.Owner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@AutoConfigureMockMvc
@SpringBootTest
public class OwnerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testFindAllOwners() throws Exception {
        this.mockMvc.perform(get("/owners"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].firstName", is("George")));
    }

    @Test
    public void testCreateOwner() throws Exception {
        Owner newOwner = new Owner();
        newOwner.setFirstName("John");
        newOwner.setLastName("Doe");
        newOwner.setAddress("123 Main St");
        newOwner.setCity("Anytown");
        newOwner.setTelephone("1234567890");


        this.mockMvc.perform(post("/owners")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newOwner)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", is("John")))
                .andExpect(jsonPath("$.lastName", is("Doe")));
    }

    @Test
    public void testDeleteOwner() throws Exception {
        Owner newOwner = new Owner();
        newOwner.setFirstName("ToDelete");
        newOwner.setLastName("Owner");
        newOwner.setAddress("123 Main St");
        newOwner.setCity("Anytown");
        newOwner.setTelephone("1234567890");


        ResultActions result = this.mockMvc.perform(post("/owners")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(newOwner)));

        String response = result.andReturn().getResponse().getContentAsString();
        Integer ownerId = com.jayway.jsonpath.JsonPath.read(response, "$.id");

        this.mockMvc.perform(delete("/owners/" + ownerId))
                .andExpect(status().isOk());

        this.mockMvc.perform(get("/owners/" + ownerId))
                .andExpect(status().isNotFound());
    }
}
