package com.tecsup.petclinic.webs;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.tecsup.petclinic.entities.PetType;
import com.tecsup.petclinic.exceptions.PetTypeNotFoundException;
import com.tecsup.petclinic.services.PetTypeService;

@RestController
public class PetTypeController {

    @Autowired
    private PetTypeService petTypeService;

    @GetMapping("/pettypes")
    public List<PetType> getPetTypes() {
        return petTypeService.findAll();
    }

    @PostMapping("/pettypes")
    @ResponseStatus(HttpStatus.CREATED)
    public PetType createPetType(@RequestBody PetType petType) {
        return petTypeService.create(petType);
    }

    @GetMapping("/pettypes/{id}")
    public ResponseEntity<PetType> getPetType(@PathVariable Integer id) {
        try {
            PetType petType = petTypeService.findById(id);
            return new ResponseEntity<>(petType, HttpStatus.OK);
        } catch (PetTypeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/pettypes/{id}")
    public ResponseEntity<PetType> updatePetType(@PathVariable Integer id, @RequestBody PetType petTypeDetails) {
        try {
            PetType petType = petTypeService.findById(id);
            petType.setName(petTypeDetails.getName());
            PetType updatedPetType = petTypeService.update(petType);
            return new ResponseEntity<>(updatedPetType, HttpStatus.OK);
        } catch (PetTypeNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/pettypes/{id}")
    public ResponseEntity<String> deletePetType(@PathVariable Integer id) {
        try {
            petTypeService.delete(id);
            return new ResponseEntity<>("PetType deleted successfully", HttpStatus.OK);
        } catch (PetTypeNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
