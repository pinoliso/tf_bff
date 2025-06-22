package com.duoc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duoc.models.Inventory;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {
}
