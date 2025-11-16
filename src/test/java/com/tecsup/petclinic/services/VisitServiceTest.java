package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;

import lombok.extern.slf4j.Slf4j;

import com.tecsup.petclinic.repositories.PetRepository;

@SpringBootTest
@Slf4j
public class VisitServiceTest {

    @Autowired
    private VisitService visitService;

    @Autowired
    private PetRepository petRepository;

    @Test
    public void testCreateVisit() {
        LocalDate VISIT_DATE = LocalDate.of(2024, 11, 11);
        String VISIT_DESCRIPTION = "Regular checkup";
        
        Pet pet = petRepository.findById(1).get();

        Visit visit = new Visit();
        visit.setVisitDate(VISIT_DATE);
        visit.setDescription(VISIT_DESCRIPTION);
        visit.setPet(pet);

        Visit createdVisit = visitService.create(visit);

        assertNotNull(createdVisit.getId());
        assertEquals(VISIT_DATE, createdVisit.getVisitDate());
        assertEquals(VISIT_DESCRIPTION, createdVisit.getDescription());
    }

    @Test
    public void testFindVisitById() {
        // Assuming a visit with ID 1 exists in the database from data.sql
        Long ID = 1L;
        String EXPECTED_DESCRIPTION = "rabies shot";
        try {
            Visit visit = visitService.findById(ID);
            assertEquals(EXPECTED_DESCRIPTION, visit.getDescription());
        } catch (VisitNotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testUpdateVisit() {
        LocalDate VISIT_DATE = LocalDate.of(2024, 11, 12);
        String VISIT_DESCRIPTION = "Initial visit";
        Pet pet = petRepository.findById(1).get();

        Visit visit = new Visit();
        visit.setVisitDate(VISIT_DATE);
        visit.setDescription(VISIT_DESCRIPTION);
        visit.setPet(pet);
        Visit createdVisit = visitService.create(visit);

        String UPDATED_DESCRIPTION = "Follow-up visit";
        createdVisit.setDescription(UPDATED_DESCRIPTION);
        Visit updatedVisit = visitService.update(createdVisit);

        assertEquals(UPDATED_DESCRIPTION, updatedVisit.getDescription());
    }

    @Test
    public void testDeleteVisit() {
        LocalDate VISIT_DATE = LocalDate.of(2024, 11, 13);
        String VISIT_DESCRIPTION = "To be deleted";
        Pet pet = petRepository.findById(1).get();

        Visit visit = new Visit();
        visit.setVisitDate(VISIT_DATE);
        visit.setDescription(VISIT_DESCRIPTION);
        visit.setPet(pet);
        Visit createdVisit = visitService.create(visit);

        Long visitId = createdVisit.getId();

        try {
            visitService.delete(visitId);
        } catch (VisitNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(VisitNotFoundException.class, () -> {
            visitService.findById(visitId);
        });
    }
}
