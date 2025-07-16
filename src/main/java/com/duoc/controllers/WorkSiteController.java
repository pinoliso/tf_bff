package com.duoc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import com.duoc.models.WorkSite;
import com.duoc.services.WorkSiteService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/worksites")
public class WorkSiteController {

    @Autowired
    private final WorkSiteService workSiteService;

    public WorkSiteController(WorkSiteService workSiteService) {
        this.workSiteService = workSiteService;
    }

    @GetMapping
    public List<WorkSite> getAllWorkSites() {
        return workSiteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkSite> getWorkSiteById(@PathVariable Long id) {
        Optional<WorkSite> workSite = workSiteService.findById(id);
        return workSite
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<WorkSite> createWorkSite(@RequestBody WorkSite workSite, @AuthenticationPrincipal Jwt jwt) {
        String sub = jwt.getClaimAsString("sub");
        WorkSite created = workSiteService.saveWithUser(workSite, sub);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkSite> updateWorkSite(@PathVariable Long id, @RequestBody WorkSite workSite) {
        Optional<WorkSite> existing = workSiteService.findById(id);
        if (existing.isPresent()) {
            workSite.setId(id); 
            workSite.setUser(existing.get().getUser());
            WorkSite updated = workSiteService.save(workSite);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkSite(@PathVariable Long id) {
        Optional<WorkSite> existing = workSiteService.findById(id);
        if (existing.isPresent()) {
            workSiteService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
