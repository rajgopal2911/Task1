package com.example.task2.repository;

import com.example.task2.entities.Item;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ItemRepository extends MongoRepository<Item,String> {
    boolean existsByName(String name);
}
