package com.duoc.repositories;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.duoc.models.Carrier;
import com.duoc.models.CarrierItem;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CarrierItemRepositoryTest {

    @Autowired
    private CarrierItemRepository carrierItemRepository;

    @Autowired
    private CarrierRepository carrierRepository; 

    @Test
    void testDeleteByCarrierId() {
        Carrier carrier = new Carrier();
        carrier = carrierRepository.save(carrier);

        CarrierItem item1 = new CarrierItem();
        item1.setCarrier(carrier);

        CarrierItem item2 = new CarrierItem();
        item2.setCarrier(carrier);

        carrierItemRepository.save(item1);
        carrierItemRepository.save(item2);

        assertThat(carrierItemRepository.findAll().size()).isEqualTo(2);

        carrierItemRepository.deleteAllByCarrierId(carrier.getId());

        assertThat(carrierItemRepository.findAll().size()).isEqualTo(0);
    }
}

