package com.duoc.Services;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.duoc.models.Carrier;
import com.duoc.models.CarrierStatus;
import com.duoc.models.User;
import com.duoc.repositories.CarrierStatusRepository;
import com.duoc.repositories.UserRepository;
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
    @Autowired
    private CarrierStatusRepository carrierStatusRepository; 
    @Autowired
    private UserRepository userRepository; 

    @Test
    void testCreateCarrier() {
        User user = new User();
        user.setName("Admin");
        user.setEmail("admin@email.com");
        user.setB2cSub("sub-admin");
        user.setRole("ADMIN");
        user.setPassword("admin");
        user.setUsername("admin");
        user = userRepository.save(user);

        Carrier carrier = new Carrier();
        carrier.setArrivalTime(LocalDateTime.now());
        carrier.setCarrier("TestCarrier");
        carrier.setDepartureTime(LocalDateTime.now());
        carrier.setDriver("TestDriver");
        carrier.setDestiny("TestDestiny");
        carrier.setPatent("TestPatent");
        carrier.setUser(user);
        carrier.setTrackingNumber("TestTrackingNumber");

        CarrierStatus carrierStatus = new CarrierStatus();
        carrierStatus.setName("TestStatus");
        carrierStatus = carrierStatusRepository.save(carrierStatus);
        carrier.setStatus(carrierStatus);
        Carrier saved = carrierService.save(carrier);

        assertNotNull(saved.getId());
        assertNotNull(saved.getArrivalTime());
    }

    @Test
    void testFindCarrier() {
        User user = new User();
        user.setName("Admin");
        user.setEmail("admin@email.com");
        user.setB2cSub("sub-admin");
        user.setRole("ADMIN");
        user.setPassword("admin");
        user.setUsername("admin");
        user = userRepository.save(user);

        Carrier carrier = new Carrier();
        carrier.setArrivalTime(LocalDateTime.now());
        carrier.setCarrier("TestCarrier");
        carrier.setDepartureTime(LocalDateTime.now());
        carrier.setDriver("TestDriver");
        carrier.setDestiny("TestDestiny");
        carrier.setPatent("TestPatent");
        carrier.setUser(user);
        carrier.setTrackingNumber("TestTrackingNumber");

        CarrierStatus carrierStatus = new CarrierStatus();
        carrierStatus.setName("TestStatus");
        carrierStatus = carrierStatusRepository.save(carrierStatus);
        carrier.setStatus(carrierStatus);
        carrierService.save(carrier);

        Optional<Carrier> found = carrierService.findById(carrier.getId());
        assertNotNull(found.get());
    }
}
