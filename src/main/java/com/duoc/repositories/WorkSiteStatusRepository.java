package com.duoc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duoc.models.WorkSiteStatus;

@Repository
public interface WorkSiteStatusRepository extends JpaRepository<WorkSiteStatus, Long> {
}
