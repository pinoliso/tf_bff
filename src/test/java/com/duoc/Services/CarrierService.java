package com.duoc.Services;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.duoc.models.Carrier;
import com.duoc.services.CarrierService;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;


@SpringBootTest
@Transactional
class CarrierServiceTest {

    @Autowired
    private CarrierService carrierService;

    @Test
    void testCreateCarrier() {
        Carrier carrier = new Carrier();
        carrier.setArrivalTime(LocalDateTime.now());
        // agrega más campos si es necesario

        Carrier saved = carrierService.save(carrier);
        assertNotNull(saved.getId());
        assertNotNull(saved.getArrivalTime());
    }

    @Test
    void testFindCarrier() {
        Carrier carrier = new Carrier();
        carrier.setArrivalTime(LocalDateTime.now());
        carrierService.save(carrier);

        Optional<Carrier> found = carrierService.findById(carrier.getId());
        assertNotNull(found.get());
    }
}
