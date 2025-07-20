package com.duoc.repositories;

import com.duoc.models.Carrier;
import com.duoc.models.CarrierStatus;
import com.duoc.models.User;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CarrierRepositoryTest {

    @Autowired
    private CarrierRepository carrierRepository;
    @Autowired
    private UserRepository userRepository; 
    @Autowired
    private CarrierStatusRepository carrierStatusRepository; 

    @Test
    @DisplayName("Guardar y buscar Carrier")
    void testSaveAndFindCarrier() {
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
        carrier = carrierRepository.save(carrier);

        List<Carrier> found = carrierRepository.findByCarrier("TestCarrier");
        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0).getCarrier()).isEqualTo("TestCarrier");
    }

    @Test
    @DisplayName("Eliminar Carrier")
    void testDeleteCarrier() {
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
        carrier = carrierRepository.save(carrier);

        carrierRepository.deleteById(carrier.getId());
        assertThat(carrierRepository.findById(carrier.getId())).isEmpty();
    }
}

