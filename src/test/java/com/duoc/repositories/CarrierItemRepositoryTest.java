package com.duoc.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.duoc.models.Carrier;
import com.duoc.models.CarrierItem;
import com.duoc.models.CarrierStatus;
import com.duoc.models.Item;
import com.duoc.models.User;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;

@DataJpaTest
public class CarrierItemRepositoryTest {

    @Autowired
    private CarrierItemRepository carrierItemRepository;
    @Autowired
    private CarrierRepository carrierRepository; 
    @Autowired
    private CarrierStatusRepository carrierStatusRepository; 
    @Autowired
    private ItemRepository itemRepository; 
    @Autowired
    private UserRepository userRepository; 

    @Test
    void testDeleteByCarrierId() {

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

        Item item = new Item();
        item.setName("TestItem");
        item.setReference("TestReference");
        item.setRemovalThreshold(10);
        item = itemRepository.save(item);

        Item item2 = new Item();
        item2.setName("TestItem2");
        item2.setReference("TestReference2");
        item2.setRemovalThreshold(12);
        item2 = itemRepository.save(item2);

        CarrierItem carrierItem = new CarrierItem();
        carrierItem.setCarrier(carrier);
        carrierItem.setQuantity(3);
        carrierItem.setItem(item);
        carrierItem.setUnit("TestUnit");
        carrierItemRepository.save(carrierItem);

        CarrierItem carrierItem2 = new CarrierItem();
        carrierItem2.setCarrier(carrier);
        carrierItem2.setQuantity(4);
        carrierItem2.setItem(item2);
        carrierItem2.setUnit("TestUnit");
        carrierItemRepository.save(carrierItem2);

        assertThat(carrierItemRepository.findAll().size()).isEqualTo(2);

        carrierItemRepository.deleteAllByCarrierId(carrier.getId());

        assertThat(carrierItemRepository.findAll().size()).isEqualTo(0);
    }
}

