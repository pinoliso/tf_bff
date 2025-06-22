package com.duoc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.models.Carrier;
import com.duoc.repositories.CarrierRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarrierService {
    
    @Autowired
    private final CarrierRepository repository;

    public CarrierService(CarrierRepository repository) {
        this.repository = repository;
    }

    public List<Carrier> findAll() { return repository.findAll(); }
    public Optional<Carrier> findById(Long id) { return repository.findById(id); }
    public Carrier save(Carrier entity) { return repository.save(entity); }
    public void deleteById(Long id) { repository.deleteById(id); }
}
