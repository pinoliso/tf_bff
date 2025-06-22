package com.duoc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.models.Inventory;
import com.duoc.repositories.InventoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    
    @Autowired
    private final InventoryRepository repository;

    public InventoryService(InventoryRepository repository) {
        this.repository = repository;
    }

    public List<Inventory> findAll() { return repository.findAll(); }
    public Optional<Inventory> findById(Long id) { return repository.findById(id); }
    public Inventory save(Inventory entity) { return repository.save(entity); }
    public void deleteById(Long id) { repository.deleteById(id); }
}
