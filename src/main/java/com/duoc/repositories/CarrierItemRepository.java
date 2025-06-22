package com.duoc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duoc.models.CarrierItem;

@Repository
public interface CarrierItemRepository extends JpaRepository<CarrierItem, Long> {
}
