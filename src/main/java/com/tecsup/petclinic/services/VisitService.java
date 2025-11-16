package com.tecsup.petclinic.services;

import java.util.List;

import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exceptions.VisitNotFoundException;

public interface VisitService {

    Visit create(Visit visit);

    Visit update(Visit visit);

    void delete(Long id) throws VisitNotFoundException;

    Visit findById(Long id) throws VisitNotFoundException;

    List<Visit> findByPetId(Integer petId);

    List<Visit> findAll();
}
