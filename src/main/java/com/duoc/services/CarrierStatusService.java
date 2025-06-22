package com.duoc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.models.CarrierStatus;
import com.duoc.repositories.CarrierStatusRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarrierStatusService {
    
    @Autowired
    private final CarrierStatusRepository repository;

    public CarrierStatusService(CarrierStatusRepository repository) {
        this.repository = repository;
    }

    public List<CarrierStatus> findAll() { return repository.findAll(); }
    public Optional<CarrierStatus> findById(Long id) { return repository.findById(id); }
    public CarrierStatus save(CarrierStatus entity) { return repository.save(entity); }
    public void deleteById(Long id) { repository.deleteById(id); }
}
