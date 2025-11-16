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

import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import com.tecsup.petclinic.services.SpecialtyService;

@RestController
public class SpecialtyController {

    @Autowired
    private SpecialtyService specialtyService;

    @GetMapping("/specialties")
    public List<Specialty> getSpecialties() {
        return specialtyService.findAll();
    }

    @PostMapping("/specialties")
    @ResponseStatus(HttpStatus.CREATED)
    public Specialty createSpecialty(@RequestBody Specialty specialty) {
        return specialtyService.create(specialty);
    }

    @GetMapping("/specialties/{id}")
    public ResponseEntity<Specialty> getSpecialty(@PathVariable Integer id) {
        try {
            Specialty specialty = specialtyService.findById(id);
            return new ResponseEntity<>(specialty, HttpStatus.OK);
        } catch (SpecialtyNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/specialties/{id}")
    public ResponseEntity<Specialty> updateSpecialty(@PathVariable Integer id, @RequestBody Specialty specialtyDetails) {
        try {
            Specialty specialty = specialtyService.findById(id);
            specialty.setName(specialtyDetails.getName());
            Specialty updatedSpecialty = specialtyService.update(specialty);
            return new ResponseEntity<>(updatedSpecialty, HttpStatus.OK);
        } catch (SpecialtyNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/specialties/{id}")
    public ResponseEntity<String> deleteSpecialty(@PathVariable Integer id) {
        try {
            specialtyService.delete(id);
            return new ResponseEntity<>("Specialty deleted successfully", HttpStatus.OK);
        } catch (SpecialtyNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
