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

import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;
import com.tecsup.petclinic.services.VisitService;

@RestController
public class VisitController {

    @Autowired
    private VisitService visitService;

    @GetMapping("/visits")
    public List<Visit> getVisits() {
        return visitService.findAll();
    }

    @PostMapping("/visits")
    @ResponseStatus(HttpStatus.CREATED)
    public Visit createVisit(@RequestBody Visit visit) {
        return visitService.create(visit);
    }

    @GetMapping("/visits/{id}")
    public ResponseEntity<Visit> getVisit(@PathVariable Long id) {
        try {
            Visit visit = visitService.findById(id);
            return new ResponseEntity<>(visit, HttpStatus.OK);
        } catch (VisitNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/visits/{id}")
    public ResponseEntity<Visit> updateVisit(@PathVariable Long id, @RequestBody Visit visitDetails) {
        try {
            Visit visit = visitService.findById(id);
            visit.setVisitDate(visitDetails.getVisitDate());
            visit.setDescription(visitDetails.getDescription());
            visit.setPet(visitDetails.getPet());
            Visit updatedVisit = visitService.update(visit);
            return new ResponseEntity<>(updatedVisit, HttpStatus.OK);
        } catch (VisitNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/visits/{id}")
    public ResponseEntity<String> deleteVisit(@PathVariable Long id) {
        try {
            visitService.delete(id);
            return new ResponseEntity<>("Visit deleted successfully", HttpStatus.OK);
        } catch (VisitNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
