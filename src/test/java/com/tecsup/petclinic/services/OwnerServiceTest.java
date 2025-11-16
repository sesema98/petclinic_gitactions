package com.tecsup.petclinic.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class OwnerServiceTest {

    @Autowired
    private OwnerService ownerService;

    @Test
    public void testCreateOwner() {
        String OWNER_FIRST_NAME = "John";
        String OWNER_LAST_NAME = "Doe";
        String OWNER_ADDRESS = "123 Main St";
        String OWNER_CITY = "Anytown";
        String OWNER_TELEPHONE = "1234567890";

        Owner owner = new Owner();
        owner.setFirstName(OWNER_FIRST_NAME);
        owner.setLastName(OWNER_LAST_NAME);
        owner.setAddress(OWNER_ADDRESS);
        owner.setCity(OWNER_CITY);
        owner.setTelephone(OWNER_TELEPHONE);

        Owner createdOwner = ownerService.create(owner);

        assertNotNull(createdOwner.getId());
        assertEquals(OWNER_FIRST_NAME, createdOwner.getFirstName());
        assertEquals(OWNER_LAST_NAME, createdOwner.getLastName());
    }

    @Test
    public void testFindOwnerById() {
        // Assuming an owner with ID 1 exists in the database from data.sql
        Long ID = 1L;
        String EXPECTED_FIRST_NAME = "George";
        try {
            Owner owner = ownerService.findById(ID);
            assertEquals(EXPECTED_FIRST_NAME, owner.getFirstName());
        } catch (OwnerNotFoundException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testUpdateOwner() {
        String OWNER_FIRST_NAME = "Jane";
        String OWNER_LAST_NAME = "Doe";
        Owner owner = new Owner();
        owner.setFirstName(OWNER_FIRST_NAME);
        owner.setLastName(OWNER_LAST_NAME);
        Owner createdOwner = ownerService.create(owner);

        String UPDATED_FIRST_NAME = "Janet";
        createdOwner.setFirstName(UPDATED_FIRST_NAME);
        Owner updatedOwner = ownerService.update(createdOwner);

        assertEquals(UPDATED_FIRST_NAME, updatedOwner.getFirstName());
    }

    @Test
    public void testDeleteOwner() {
        String OWNER_FIRST_NAME = "Jim";
        String OWNER_LAST_NAME = "Beam";
        Owner owner = new Owner();
        owner.setFirstName(OWNER_FIRST_NAME);
        owner.setLastName(OWNER_LAST_NAME);
        Owner createdOwner = ownerService.create(owner);

        Long ownerId = createdOwner.getId();

        try {
            ownerService.delete(ownerId);
        } catch (OwnerNotFoundException e) {
            fail(e.getMessage());
        }

        assertThrows(OwnerNotFoundException.class, () -> {
            ownerService.findById(ownerId);
        });
    }
}