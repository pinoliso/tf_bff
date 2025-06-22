package com.duoc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.models.WorkSite;
import com.duoc.repositories.WorkSiteRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkSiteService {
    
    @Autowired
    private final WorkSiteRepository repository;

    public WorkSiteService(WorkSiteRepository repository) {
        this.repository = repository;
    }

    public List<WorkSite> findAll() { return repository.findAll(); }
    public Optional<WorkSite> findById(Long id) { return repository.findById(id); }
    public WorkSite save(WorkSite entity) { return repository.save(entity); }
    public void deleteById(Long id) { repository.deleteById(id); }
}
