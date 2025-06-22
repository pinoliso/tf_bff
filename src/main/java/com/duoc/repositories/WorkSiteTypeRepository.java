package com.duoc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duoc.models.WorkSiteType;

@Repository
public interface WorkSiteTypeRepository extends JpaRepository<WorkSiteType, Long> {
}
