package com.example.task2.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface Item extends MongoRepository<com.example.task2.entities.Item,String> {
    boolean existsByName(String name);
}
