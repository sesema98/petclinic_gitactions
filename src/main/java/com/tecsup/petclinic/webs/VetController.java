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

import com.tecsup.petclinic.entities.Vet;
import com.tecsup.petclinic.exceptions.VetNotFoundException;
import com.tecsup.petclinic.services.VetService;

@RestController
public class VetController {

    @Autowired
    private VetService vetService;

    @GetMapping("/vets")
    public List<Vet> getVets() {
        return vetService.findAll();
    }

    @PostMapping("/vets")
    @ResponseStatus(HttpStatus.CREATED)
    public Vet createVet(@RequestBody Vet vet) {
        return vetService.create(vet);
    }

    @GetMapping("/vets/{id}")
    public ResponseEntity<Vet> getVet(@PathVariable Integer id) {
        try {
            Vet vet = vetService.findById(id);
            return new ResponseEntity<>(vet, HttpStatus.OK);
        } catch (VetNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/vets/{id}")
    public ResponseEntity<Vet> updateVet(@PathVariable Integer id, @RequestBody Vet vetDetails) {
        try {
            Vet vet = vetService.findById(id);
            vet.setFirstName(vetDetails.getFirstName());
            vet.setLastName(vetDetails.getLastName());
            vet.setSpecialties(vetDetails.getSpecialties());
            Vet updatedVet = vetService.update(vet);
            return new ResponseEntity<>(updatedVet, HttpStatus.OK);
        } catch (VetNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/vets/{id}")
    public ResponseEntity<String> deleteVet(@PathVariable Integer id) {
        try {
            vetService.delete(id);
            return new ResponseEntity<>("Vet deleted successfully", HttpStatus.OK);
        } catch (VetNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
