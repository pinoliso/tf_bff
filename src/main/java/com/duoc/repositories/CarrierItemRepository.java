package com.duoc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.duoc.models.CarrierItem;

@Repository
public interface CarrierItemRepository extends JpaRepository<CarrierItem, Long> {
    @Modifying
    @Transactional
    @Query("DELETE FROM CarrierItem ci WHERE ci.carrier.id = :carrierId")
    void deleteAllByCarrierId(@Param("carrierId") Long carrierId);
}
