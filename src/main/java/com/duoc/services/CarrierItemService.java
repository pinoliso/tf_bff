package com.duoc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.models.CarrierItem;
import com.duoc.repositories.CarrierItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarrierItemService {
    
    @Autowired
    private final CarrierItemRepository repository;

    public CarrierItemService(CarrierItemRepository repository) {
        this.repository = repository;
    }

    public List<CarrierItem> findAll() { return repository.findAll(); }
    public Optional<CarrierItem> findById(Long id) { return repository.findById(id); }
    public CarrierItem save(CarrierItem entity) { return repository.save(entity); }
    public void deleteById(Long id) { repository.deleteById(id); }
}
