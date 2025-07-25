package com.duoc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.duoc.models.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
}
