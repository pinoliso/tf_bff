package com.duoc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.models.WorkSiteStatus;
import com.duoc.repositories.WorkSiteStatusRepository;

import java.util.List;
import java.util.Optional;

@Service
public class WorkSiteStatusService {
    
    @Autowired
    private final WorkSiteStatusRepository repository;

    public WorkSiteStatusService(WorkSiteStatusRepository repository) {
        this.repository = repository;
    }

    public List<WorkSiteStatus> findAll() { return repository.findAll(); }
    public Optional<WorkSiteStatus> findById(Long id) { return repository.findById(id); }
    public WorkSiteStatus save(WorkSiteStatus entity) { return repository.save(entity); }
    public void deleteById(Long id) { repository.deleteById(id); }
}
