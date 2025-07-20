package com.duoc.controllers;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.duoc.config.TestSecurityConfig;
import com.duoc.models.Carrier;
import com.duoc.models.Inventory;
import com.duoc.models.InventoryStatus;
import com.duoc.models.User;
import com.duoc.models.WorkSite;
import com.duoc.models.WorkSiteStatus;
import com.duoc.models.WorkSiteType;
import com.duoc.repositories.CarrierRepository;
import com.duoc.repositories.UserRepository;
import com.duoc.repositories.WorkSiteRepository;
import com.duoc.repositories.WorkSiteStatusRepository;
import com.duoc.repositories.WorkSiteTypeRepository;
import com.duoc.services.InventoryService;
import com.duoc.services.InventoryStatusService;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;


@WebMvcTest(InventoryController.class)
@Import(TestSecurityConfig.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private InventoryService inventoryService;
    @MockBean
    private CarrierRepository carrierRepository;
    @MockBean
    private UserRepository userRepository;
    @MockBean
    private InventoryStatusService inventoryStatusService;
    @MockBean
    private WorkSiteRepository workSiteRepository;
    @MockBean
    private WorkSiteStatusRepository workSiteStatusRepository;
    @MockBean
    private WorkSiteTypeRepository workSiteTypeRepository;

    @Test
    void testCreateInventory() throws Exception {

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
        carrierRepository.save(carrier);

        InventoryStatus inventoryStatus = new InventoryStatus();
        inventoryStatus.setId(1L);
        inventoryStatus.setName("TestStatus");
        inventoryStatus = inventoryStatusService.save(inventoryStatus);

        WorkSiteStatus status = new WorkSiteStatus();
        status.setName("Activo");
        status = workSiteStatusRepository.save(status);

        WorkSiteType type = new WorkSiteType();
        type.setName("Tipo 1");
        type = workSiteTypeRepository.save(type);

        WorkSite workSite = new WorkSite();
        workSite.setName("Sitio 123");
        workSite.setUser(user);
        workSite.setWorkSiteStatus(status);
        workSite.setWorkSiteType(type);
        workSite = workSiteRepository.save(workSite);

        Inventory inventory = new Inventory();
        inventory.setId(1L);
        inventory.setCarrier(carrier);
        inventory.setInventoryStatus(inventoryStatus);
        inventory.setUser(user);
        inventory.setWorkSite(workSite);
        inventory.setComments("Inventario Prueba");

        when(inventoryService.saveWithUserWithItems(any(Inventory.class), any(String.class))).thenReturn(inventory);

        mockMvc.perform(post("/api/inventories")
                .contentType(MediaType.APPLICATION_JSON)
                .with(jwt().jwt(jwt -> jwt.claim("sub", "sub-admin").claim("email", "admin@email.com").claim("role", "ADMIN").claim("name", "Admin")))
                .content("""
                    { "comments": "Inventario Prueba" }
                """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.comments").value("Inventario Prueba"));
    }

    @Test
    void testGetInventoryById() throws Exception {
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
        carrierRepository.save(carrier);

        InventoryStatus inventoryStatus = new InventoryStatus();
        inventoryStatus.setId(1L);
        inventoryStatus.setName("TestStatus");
        inventoryStatus = inventoryStatusService.save(inventoryStatus);

        WorkSiteStatus status = new WorkSiteStatus();
        status.setName("Activo");
        status = workSiteStatusRepository.save(status);

        WorkSiteType type = new WorkSiteType();
        type.setName("Tipo 1");
        type = workSiteTypeRepository.save(type);

        WorkSite workSite = new WorkSite();
        workSite.setName("Sitio 123");
        workSite.setUser(user);
        workSite.setWorkSiteStatus(status);
        workSite.setWorkSiteType(type);
        workSite = workSiteRepository.save(workSite);

        Inventory inventory = new Inventory();
        inventory.setId(1L);
        inventory.setCarrier(carrier);
        inventory.setInventoryStatus(inventoryStatus);
        inventory.setUser(user);
        inventory.setWorkSite(workSite);
        inventory.setComments("Inventario Prueba");

        when(inventoryService.findById(1L)).thenReturn(Optional.of(inventory));

        mockMvc.perform(get("/api/inventories/1")
            .with(jwt().jwt(jwt -> jwt.claim("sub", "sub-admin").claim("email", "admin@email.com").claim("role", "ADMIN").claim("name", "Admin"))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.comments").value("Inventario Prueba"));
    }


}

