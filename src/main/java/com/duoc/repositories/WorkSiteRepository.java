package com.duoc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duoc.models.WorkSite;

@Repository
public interface WorkSiteRepository extends JpaRepository<WorkSite, Long> {
}
