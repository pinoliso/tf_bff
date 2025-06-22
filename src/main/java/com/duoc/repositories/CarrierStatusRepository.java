package com.duoc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duoc.models.CarrierStatus;

@Repository
public interface CarrierStatusRepository extends JpaRepository<CarrierStatus, Long> {
}
