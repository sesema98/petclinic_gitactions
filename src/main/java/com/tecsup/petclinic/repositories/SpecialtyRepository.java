package com.tecsup.petclinic.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tecsup.petclinic.entities.Specialty;

@Repository
public interface SpecialtyRepository extends CrudRepository<Specialty, Integer> {

    List<Specialty> findByName(String name);

    @Override
    List<Specialty> findAll();
}
