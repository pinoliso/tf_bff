package com.duoc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duoc.models.Carrier;

@Repository
public interface CarrierRepository extends JpaRepository<Carrier, Long> {
}
