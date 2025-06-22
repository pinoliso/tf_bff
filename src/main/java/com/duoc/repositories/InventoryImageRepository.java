package com.duoc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duoc.models.InventoryImage;

@Repository
public interface InventoryImageRepository extends JpaRepository<InventoryImage, Long> {
}
