package com.duoc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duoc.models.WorkSite;

@Repository
public interface WorkSiteRepository extends JpaRepository<WorkSite, Long> {

    public List<WorkSite> findByName(String string);
}
