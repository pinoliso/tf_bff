package com.duoc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.models.InventoryImage;
import com.duoc.repositories.InventoryImageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryImageService {
    
    @Autowired
    private final InventoryImageRepository repository;

    public InventoryImageService(InventoryImageRepository repository) {
        this.repository = repository;
    }

    public List<InventoryImage> findAll() { return repository.findAll(); }
    public Optional<InventoryImage> findById(Long id) { return repository.findById(id); }
    public InventoryImage save(InventoryImage entity) { return repository.save(entity); }
    public void deleteById(Long id) { repository.deleteById(id); }
}
