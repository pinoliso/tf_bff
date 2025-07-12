package com.duoc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.duoc.models.Carrier;
import com.duoc.models.User;
import com.duoc.repositories.CarrierRepository;
import com.duoc.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CarrierService {
    
    @Autowired
    private final CarrierRepository repository;
    
    @Autowired
    private final UserRepository userRepository;

    public CarrierService(CarrierRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<Carrier> findAll() { return repository.findAll(); }
    public Optional<Carrier> findById(Long id) { return repository.findById(id); }
    public Carrier save(Carrier entity) { return repository.save(entity); }
    public void deleteById(Long id) { repository.deleteById(id); }

    public Carrier saveWithUser(Carrier carrier, String sub) {
        User user = userRepository.findByB2cSub(sub).get();
        carrier.setUser(user);
        return repository.save(carrier); 
    }
}
