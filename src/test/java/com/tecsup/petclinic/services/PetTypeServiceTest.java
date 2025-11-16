package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tecsup.petclinic.entities.PetType;
import com.tecsup.petclinic.exceptions.PetTypeNotFoundException;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class PetTypeServiceTest {

    @Autowired
    private PetTypeService petTypeService;

    @Test
    public void testCreatePetType() {
        String PET_TYPE_NAME = "Fish";

        PetType petType = new PetType();
        petType.setName(PET_TYPE_NAME);

        PetType createdPetType = petTypeService.create(petType);

        assertNotNull(createdPetType.getId());
        assertEquals(PET_TYPE_NAME, createdPetType.getName());
    }

    @Test
    public void testFindPetTypeById() {
        // Assuming a pet type with ID 1 exists in the database from data.sql
        Integer ID = 1;
        String EXPECTED_NAME = "cat";
        try {
            PetType petType = petTypeService.findById(ID);
            assertEquals(EXPECTED_NAME, petType.getName());
        } catch (PetTypeNotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testUpdatePetType() {
        String PET_TYPE_NAME = "Bird";
        PetType petType = new PetType();
        petType.setName(PET_TYPE_NAME);
        PetType createdPetType = petTypeService.create(petType);

        String UPDATED_NAME = "Parrot";
        createdPetType.setName(UPDATED_NAME);
        PetType updatedPetType = petTypeService.update(createdPetType);

        assertEquals(UPDATED_NAME, updatedPetType.getName());
    }

    @Test
    public void testDeletePetType() {
        String PET_TYPE_NAME = "Goldfish";
        PetType petType = new PetType();
        petType.setName(PET_TYPE_NAME);
        PetType createdPetType = petTypeService.create(petType);

        Integer petTypeId = createdPetType.getId();

        try {
            petTypeService.delete(petTypeId);
        } catch (PetTypeNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(PetTypeNotFoundException.class, () -> {
            petTypeService.findById(petTypeId);
        });
    }
}
