package com.duoc.repositories;

import com.duoc.models.Carrier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class CarrierRepositoryTest {

    @Autowired
    private CarrierRepository carrierRepository;

    @Test
    @DisplayName("Guardar y buscar Carrier")
    void testSaveAndFindCarrier() {
        Carrier carrier = new Carrier();
        carrier.setCarrier("TestCarrier");
        carrier = carrierRepository.save(carrier);

        List<Carrier> found = carrierRepository.findByCarrier("TestCarrier");
        assertThat(found.size()).isEqualTo(1);
        assertThat(found.get(0).getCarrier()).isEqualTo("TestCarrier");
    }

    @Test
    @DisplayName("Eliminar Carrier")
    void testDeleteCarrier() {
        Carrier carrier = new Carrier();
        carrier.setCarrier("ToDelete");
        carrier = carrierRepository.save(carrier);

        carrierRepository.deleteById(carrier.getId());
        assertThat(carrierRepository.findById(carrier.getId())).isEmpty();
    }
}

