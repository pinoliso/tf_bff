package com.duoc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.models.WorkSiteType;
import com.duoc.repositories.WorkSiteTypeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkSiteTypeService {
    
    @Autowired
    private final WorkSiteTypeRepository repository;

    public WorkSiteTypeService(WorkSiteTypeRepository repository) {
        this.repository = repository;
    }

    public List<WorkSiteType> findAll() { return repository.findAll(); }
    public Optional<WorkSiteType> findById(Long id) { return repository.findById(id); }
    public WorkSiteType save(WorkSiteType entity) { return repository.save(entity); }
    public void deleteById(Long id) { repository.deleteById(id); }
}
