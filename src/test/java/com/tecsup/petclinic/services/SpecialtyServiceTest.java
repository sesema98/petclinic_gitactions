package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class SpecialtyServiceTest {

    @Autowired
    private SpecialtyService specialtyService;

    @Test
    public void testCreateSpecialty() {
        String SPECIALTY_NAME = "Cardiology";

        Specialty specialty = new Specialty();
        specialty.setName(SPECIALTY_NAME);

        Specialty createdSpecialty = specialtyService.create(specialty);

        assertNotNull(createdSpecialty.getId());
        assertEquals(SPECIALTY_NAME, createdSpecialty.getName());
    }

    @Test
    public void testFindSpecialtyById() {
        // Assuming a specialty with ID 1 exists in the database from data.sql
        Integer ID = 1;
        String EXPECTED_NAME = "radiology";
        try {
            Specialty specialty = specialtyService.findById(ID);
            assertEquals(EXPECTED_NAME, specialty.getName());
        } catch (SpecialtyNotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testUpdateSpecialty() {
        String SPECIALTY_NAME = "Neurology";
        Specialty specialty = new Specialty();
        specialty.setName(SPECIALTY_NAME);
        Specialty createdSpecialty = specialtyService.create(specialty);

        String UPDATED_NAME = "Oncology";
        createdSpecialty.setName(UPDATED_NAME);
        Specialty updatedSpecialty = specialtyService.update(createdSpecialty);

        assertEquals(UPDATED_NAME, updatedSpecialty.getName());
    }

    @Test
    public void testDeleteSpecialty() {
        String SPECIALTY_NAME = "Dermatology";
        Specialty specialty = new Specialty();
        specialty.setName(SPECIALTY_NAME);
        Specialty createdSpecialty = specialtyService.create(specialty);

        Integer specialtyId = createdSpecialty.getId();

        try {
            specialtyService.delete(specialtyId);
        } catch (SpecialtyNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(SpecialtyNotFoundException.class, () -> {
            specialtyService.findById(specialtyId);
        });
    }
}
