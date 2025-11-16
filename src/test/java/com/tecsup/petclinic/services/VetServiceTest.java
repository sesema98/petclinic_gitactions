package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class VetServiceTest {

    @Autowired
    private VetService vetService;

    @Test
    public void testCreateVet() {
        String VET_FIRST_NAME = "John";
        String VET_LAST_NAME = "Doe";

        Vet vet = new Vet();
        vet.setFirstName(VET_FIRST_NAME);
        vet.setLastName(VET_LAST_NAME);

        Vet createdVet = vetService.create(vet);

        assertNotNull(createdVet.getId());
        assertEquals(VET_FIRST_NAME, createdVet.getFirstName());
        assertEquals(VET_LAST_NAME, createdVet.getLastName());
    }

    @Test
    public void testFindVetById() {
        // Assuming a vet with ID 1 exists in the database from data.sql
        Integer ID = 1;
        String EXPECTED_FIRST_NAME = "James";
        try {
            Vet vet = vetService.findById(ID);
            assertEquals(EXPECTED_FIRST_NAME, vet.getFirstName());
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testUpdateVet() {
        String VET_FIRST_NAME = "Jane";
        String VET_LAST_NAME = "Doe";
        Vet vet = new Vet();
        vet.setFirstName(VET_FIRST_NAME);
        vet.setLastName(VET_LAST_NAME);
        Vet createdVet = vetService.create(vet);

        String UPDATED_FIRST_NAME = "Janet";
        createdVet.setFirstName(UPDATED_FIRST_NAME);
        Vet updatedVet = vetService.update(createdVet);

        assertEquals(UPDATED_FIRST_NAME, updatedVet.getFirstName());
    }

    @Test
    public void testDeleteVet() {
        String VET_FIRST_NAME = "Jim";
        String VET_LAST_NAME = "Beam";
        Vet vet = new Vet();
        vet.setFirstName(VET_FIRST_NAME);
        vet.setLastName(VET_LAST_NAME);
        Vet createdVet = vetService.create(vet);

        Integer vetId = createdVet.getId();

        try {
            vetService.delete(vetId);
        } catch (VetNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(VetNotFoundException.class, () -> {
            vetService.findById(vetId);
        });
    }
}