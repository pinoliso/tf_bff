package com.duoc.controllers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import com.duoc.config.TestSecurityConfig;
import com.duoc.models.Carrier;
import com.duoc.models.CarrierStatus;
import com.duoc.models.User;
import com.duoc.repositories.CarrierStatusRepository;
import com.duoc.repositories.UserRepository;
import com.duoc.services.CarrierService;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

@WebMvcTest(CarrierController.class)
@Import(TestSecurityConfig.class)
class CarrierControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private CarrierService carrierService;
    @MockBean
    private CarrierStatusRepository carrierStatusRepository; 
    @MockBean
    private UserRepository userRepository; 

    @Test
    void testCreateCarrier() throws Exception {
        User user = new User();
        user.setName("Admin");
        user.setEmail("admin@email.com");
        user.setB2cSub("sub-admin");
        user.setRole("ADMIN");
        user.setPassword("admin");
        user.setUsername("admin");
        user = userRepository.save(user);

        Carrier carrier = new Carrier();
        carrier.setId(1L);
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

        when(carrierService.saveWithUser(any(Carrier.class), any(String.class))).thenReturn(carrier);

        mockMvc.perform(post("/api/carriers")
                .contentType(MediaType.APPLICATION_JSON)
                .with(jwt().jwt(jwt -> jwt.claim("sub", "sub-admin").claim("email", "admin@email.com").claim("role", "ADMIN").claim("name", "Admin")))
                .content("""
                    { "arrivalTime": "2025-07-17T22:30:00", "carrier": "TestCarrier", "departureTime": "2025-07-17T22:30:00", "driver": "TestDriver", "destiny": "TestDestiny", "patent": "TestPatent", "trackingNumber": "TestTrackingNumber" }
                """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L));
    }
}

