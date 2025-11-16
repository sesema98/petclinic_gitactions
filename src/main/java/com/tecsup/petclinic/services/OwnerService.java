package com.tecsup.petclinic.services;

import java.util.List;

import com.tecsup.petclinic.entities.Owner;
import com.tecsup.petclinic.exceptions.OwnerNotFoundException;

public interface OwnerService {

    Owner create(Owner owner);

    Owner update(Owner owner);

    void delete(Long id) throws OwnerNotFoundException;

    Owner findById(Long id) throws OwnerNotFoundException;

    List<Owner> findByFirstName(String firstName);

    List<Owner> findByLastName(String lastName);

    List<Owner> findAll();
}
