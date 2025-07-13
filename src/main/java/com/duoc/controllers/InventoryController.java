package com.duoc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import com.duoc.models.Inventory;
import com.duoc.models.InventoryStatus;
import com.duoc.services.InventoryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {

    @Autowired
    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // Listar todos los inventarios
    @GetMapping
    public List<Inventory> getAllInventories() {
        return inventoryService.findAll();
    }

    // Buscar inventario por ID
    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        Optional<Inventory> inventory = inventoryService.findById(id);
        return inventory
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear nuevo inventario
    @PostMapping
    public ResponseEntity<Inventory> createInventory(@RequestBody Inventory inventory, @AuthenticationPrincipal Jwt jwt) {
        String sub = jwt.getClaimAsString("sub");
        Inventory created = inventoryService.saveWithUserWithItems(inventory, sub);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // Actualizar inventario existente
    @PutMapping("/{id}")
    public ResponseEntity<Inventory> updateInventory(@PathVariable Long id, @RequestBody Inventory inventory) {
        Optional<Inventory> existing = inventoryService.findById(id);
        if (existing.isPresent()) {
            inventory.setId(id);
            Inventory updated = inventoryService.save(inventory);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar inventario por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        Optional<Inventory> existing = inventoryService.findById(id);
        if (existing.isPresent()) {
            inventoryService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/{id}/status/{status}")
    public ResponseEntity<Inventory> updateInventoryStatus(@PathVariable Long id, @PathVariable Long status) {
        Optional<Inventory> existing = inventoryService.findById(id);
        if (existing.isPresent()) {
            Inventory inv = existing.get();
            inv.setId(id);
            inv.setInventoryStatus(new InventoryStatus(status));
            Inventory updated = inventoryService.save(inv);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

