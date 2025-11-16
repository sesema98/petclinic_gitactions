package com.tecsup.petclinic.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecsup.petclinic.entities.PetType;
import com.tecsup.petclinic.exceptions.PetTypeNotFoundException;
import com.tecsup.petclinic.repositories.PetTypeRepository;

@Service
public class PetTypeServiceImpl implements PetTypeService {

    private static final Logger logger = LoggerFactory.getLogger(PetTypeServiceImpl.class);

    @Autowired
    private PetTypeRepository petTypeRepository;

    @Override
    public PetType create(PetType petType) {
        return petTypeRepository.save(petType);
    }

    @Override
    public PetType update(PetType petType) {
        return petTypeRepository.save(petType);
    }

    @Override
    public void delete(Integer id) throws PetTypeNotFoundException {
        PetType petType = findById(id);
        petTypeRepository.delete(petType);
    }

    @Override
    public PetType findById(Integer id) throws PetTypeNotFoundException {
        Optional<PetType> petType = petTypeRepository.findById(id);
        if (!petType.isPresent()) {
            throw new PetTypeNotFoundException("Record not found...!");
        }
        return petType.get();
    }

    @Override
    public List<PetType> findByName(String name) {
        return petTypeRepository.findByName(name);
    }

    @Override
    public List<PetType> findAll() {
        return petTypeRepository.findAll();
    }
}
