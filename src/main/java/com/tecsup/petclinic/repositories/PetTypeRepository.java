package com.tecsup.petclinic.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tecsup.petclinic.entities.PetType;

@Repository
public interface PetTypeRepository extends CrudRepository<PetType, Integer> {

    List<PetType> findByName(String name);

    @Override
    List<PetType> findAll();
}
