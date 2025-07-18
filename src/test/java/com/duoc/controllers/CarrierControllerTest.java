package com.duoc.controllers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import com.duoc.models.Carrier;
import com.duoc.services.CarrierService;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

@WebMvcTest(CarrierController.class)
class CarrierControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarrierService carrierService;

    @Test
    void testCreateCarrier() throws Exception {
        Carrier carrier = new Carrier();
        carrier.setId(1L);
        carrier.setArrivalTime(LocalDateTime.now());

        when(carrierService.save(any(Carrier.class))).thenReturn(carrier);

        mockMvc.perform(post("/api/carriers")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    { "arrivalTime": "2025-07-17T22:30:00" }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L));
    }
}

