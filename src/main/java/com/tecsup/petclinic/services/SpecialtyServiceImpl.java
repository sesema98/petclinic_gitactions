package com.tecsup.petclinic.services;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tecsup.petclinic.entities.Specialty;
import com.tecsup.petclinic.exceptions.SpecialtyNotFoundException;
import com.tecsup.petclinic.repositories.SpecialtyRepository;

@Service
public class SpecialtyServiceImpl implements SpecialtyService {

    private static final Logger logger = LoggerFactory.getLogger(SpecialtyServiceImpl.class);

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Override
    public Specialty create(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    @Override
    public Specialty update(Specialty specialty) {
        return specialtyRepository.save(specialty);
    }

    @Override
    public void delete(Integer id) throws SpecialtyNotFoundException {
        Specialty specialty = findById(id);
        specialtyRepository.delete(specialty);
    }

    @Override
    public Specialty findById(Integer id) throws SpecialtyNotFoundException {
        Optional<Specialty> specialty = specialtyRepository.findById(id);
        if (!specialty.isPresent()) {
            throw new SpecialtyNotFoundException("Record not found...!");
        }
        return specialty.get();
    }

    @Override
    public List<Specialty> findByName(String name) {
        return specialtyRepository.findByName(name);
    }

    @Override
    public List<Specialty> findAll() {
        return specialtyRepository.findAll();
    }
}
