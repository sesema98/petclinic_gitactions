package com.tecsup.petclinic.services;

import java.util.List;

import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;

public interface SpecialtyService {

    Specialty create(Specialty specialty);

    Specialty update(Specialty specialty);

    void delete(Integer id) throws SpecialtyNotFoundException;

    Specialty findById(Integer id) throws SpecialtyNotFoundException;

    List<Specialty> findByName(String name);

    List<Specialty> findAll();
}
