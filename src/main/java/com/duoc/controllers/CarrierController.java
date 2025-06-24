package com.duoc.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.duoc.models.Carrier;
import com.duoc.services.CarrierService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/carriers")
public class CarrierController {

    @Autowired
    private final CarrierService carrierService;

    public CarrierController(CarrierService carrierService) {
        this.carrierService = carrierService;
    }

    @GetMapping
    public List<Carrier> getAllCarriers() {
        return carrierService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrier> getCarrierById(@PathVariable Long id) {
        Optional<Carrier> carrier = carrierService.findById(id);
        return carrier
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Carrier> createCarrier(@RequestBody Carrier carrier) {
        Carrier created = carrierService.save(carrier);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Carrier> updateCarrier(@PathVariable Long id, @RequestBody Carrier carrier) {
        Optional<Carrier> existing = carrierService.findById(id);
        if (existing.isPresent()) {
            carrier.setId(id);
            Carrier updated = carrierService.save(carrier);
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarrier(@PathVariable Long id) {
        Optional<Carrier> existing = carrierService.findById(id);
        if (existing.isPresent()) {
            carrierService.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
