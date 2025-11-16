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

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;
import com.tecsup.petclinic.services.OwnerService;

@RestController
public class OwnerController {

    @Autowired
    private OwnerService ownerService;

    @GetMapping("/owners")
    public List<Owner> getOwners() {
        return ownerService.findAll();
    }

    @PostMapping("/owners")
    @ResponseStatus(HttpStatus.CREATED)
    public Owner createOwner(@RequestBody Owner owner) {
        return ownerService.create(owner);
    }

    @GetMapping("/owners/{id}")
    public ResponseEntity<Owner> getOwner(@PathVariable Long id) {
        try {
            Owner owner = ownerService.findById(id);
            return new ResponseEntity<>(owner, HttpStatus.OK);
        } catch (OwnerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/owners/{id}")
    public ResponseEntity<Owner> updateOwner(@PathVariable Long id, @RequestBody Owner ownerDetails) {
        try {
            Owner owner = ownerService.findById(id);
            owner.setFirstName(ownerDetails.getFirstName());
            owner.setLastName(ownerDetails.getLastName());
            owner.setAddress(ownerDetails.getAddress());
            owner.setCity(ownerDetails.getCity());
            owner.setTelephone(ownerDetails.getTelephone());
            Owner updatedOwner = ownerService.update(owner);
            return new ResponseEntity<>(updatedOwner, HttpStatus.OK);
        } catch (OwnerNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/owners/{id}")
    public ResponseEntity<String> deleteOwner(@PathVariable Long id) {
        try {
            ownerService.delete(id);
            return new ResponseEntity<>("Owner deleted successfully", HttpStatus.OK);
        } catch (OwnerNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
