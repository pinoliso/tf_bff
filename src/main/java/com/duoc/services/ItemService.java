package com.duoc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.models.Item;
import com.duoc.repositories.ItemRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ItemService {
    
    @Autowired
    private final ItemRepository repository;

    public ItemService(ItemRepository repository) {
        this.repository = repository;
    }

    public List<Item> findAll() { return repository.findAll(); }
    public Optional<Item> findById(Long id) { return repository.findById(id); }
    public Item save(Item entity) { return repository.save(entity); }
    public void deleteById(Long id) { repository.deleteById(id); }
}
