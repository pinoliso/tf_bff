package com.duoc.controllers;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.duoc.config.TestSecurityConfig;
import com.duoc.models.Item;
import com.duoc.services.ItemService;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

@WebMvcTest(ItemController.class)
@Import(TestSecurityConfig.class)
class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @Test
    void testCreateItem() throws Exception {
        Item item = new Item();
        item.setId(1L);
        item.setReference("REF-123");
        item.setName("Botella Plástica");

        when(itemService.save(any(Item.class))).thenReturn(item);

        mockMvc.perform(post("/api/items")
                .contentType(MediaType.APPLICATION_JSON)
                .with(jwt().jwt(jwt -> jwt.claim("sub", "sub-admin").claim("email", "admin@email.com").claim("role", "ADMIN").claim("name", "Admin")))
                .content("""
                    { "name": "Botella Plástica" }
                """))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Botella Plástica"));
    }

    @Test
    void testGetItemById() throws Exception {
        Item item = new Item();
        item.setId(1L);
        item.setReference("REF-123");
        item.setName("Botella Plástica");

        when(itemService.findById(1L)).thenReturn(Optional.of(item));

        mockMvc.perform(get("/api/items/1")
            .with(jwt().jwt(jwt -> jwt.claim("sub", "sub-admin").claim("email", "admin@email.com").claim("role", "ADMIN").claim("name", "Admin"))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1L))
            .andExpect(jsonPath("$.name").value("Botella Plástica"));
    }

}

