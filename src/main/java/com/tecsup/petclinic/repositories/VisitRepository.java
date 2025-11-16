package com.tecsup.petclinic.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tecsup.petclinic.entities.Visit;

@Repository
public interface VisitRepository extends CrudRepository<Visit, Long> {

    List<Visit> findByPetId(Integer petId);

    @Override
    List<Visit> findAll();
}
