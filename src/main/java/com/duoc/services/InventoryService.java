package com.duoc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.models.Inventory;
import com.duoc.models.InventoryItem;
import com.duoc.models.InventoryStatus;
import com.duoc.models.User;
import com.duoc.repositories.InventoryRepository;
import com.duoc.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class InventoryService {
    
    @Autowired
    private final InventoryRepository repository;
    
    @Autowired
    private final UserRepository userRepository;

    public InventoryService(InventoryRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<Inventory> findAll() { return repository.findAll(); }
    public Optional<Inventory> findById(Long id) { return repository.findById(id); }
    public Inventory save(Inventory entity) { return repository.save(entity); }
    public void deleteById(Long id) { repository.deleteById(id); }
    public Inventory saveWithItems(Inventory inventory) {
        if (inventory.getItems() != null) {
            for (InventoryItem item : inventory.getItems()) {
                item.setInventory(inventory);
            }
        }
        return repository.save(inventory);
    }

    public Inventory saveWithUserWithItems(Inventory inventory, String sub) {
        User user = new User();
        user.setId(1L);
        inventory.setUser(user);
        for (InventoryItem item : inventory.getItems()) {
            item.setInventory(inventory); 
            InventoryStatus status = new InventoryStatus();
            status.setId(1L);
            item.setInventoryStatus(status);
        }
        return repository.save(inventory);
    }
}
