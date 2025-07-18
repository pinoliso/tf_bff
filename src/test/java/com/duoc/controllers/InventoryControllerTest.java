package com.duoc.controllers;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import com.duoc.models.Inventory;
import com.duoc.services.InventoryService;


@WebMvcTest(InventoryController.class)
class InventoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InventoryService inventoryService;

    @Test
    void testCreateInventory() throws Exception {
        Inventory inventory = new Inventory();
        inventory.setId(1L);
        inventory.setComments("Inventario Prueba");

        // Simula el retorno del servicio
        when(inventoryService.save(any(Inventory.class))).thenReturn(inventory);

        mockMvc.perform(post("/api/inventories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                    { "comments": "Inventario Prueba" }
                """))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.comments").value("Inventario Prueba"));
    }

    @Test
    void testGetInventoryById() throws Exception {
        Inventory inventory = new Inventory();
        inventory.setId(1L);
        inventory.setComments("Inventario Prueba");

        when(inventoryService.findById(1L)).thenReturn(Optional.of(inventory));

        mockMvc.perform(get("/api/inventories/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Inventario Prueba"));
    }


}

