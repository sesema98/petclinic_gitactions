package com.tecsup.petclinic.services;

import java.util.List;

import com.tecsup.petclinic.entities.PetType;
import com.tecsup.petclinic.exceptions.PetTypeNotFoundException;

public interface PetTypeService {

    PetType create(PetType petType);

    PetType update(PetType petType);

    void delete(Integer id) throws PetTypeNotFoundException;

    PetType findById(Integer id) throws PetTypeNotFoundException;

    List<PetType> findByName(String name);

    List<PetType> findAll();
}
