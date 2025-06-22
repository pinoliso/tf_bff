package com.duoc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.models.InventoryStatus;
import com.duoc.repositories.InventoryStatusRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryStatusService {
    
    @Autowired
    private final InventoryStatusRepository repository;

    public InventoryStatusService(InventoryStatusRepository repository) {
        this.repository = repository;
    }

    public List<InventoryStatus> findAll() { return repository.findAll(); }
    public Optional<InventoryStatus> findById(Long id) { return repository.findById(id); }
    public InventoryStatus save(InventoryStatus entity) { return repository.save(entity); }
    public void deleteById(Long id) { repository.deleteById(id); }
}
