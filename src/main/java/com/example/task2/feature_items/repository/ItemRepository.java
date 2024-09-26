package com.example.task2.feature_items.repository;

import com.example.task2.feature_items.entities.mongo.Item;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends MongoRepository<Item, String> {
  boolean existsByName(String name);

  //    Page<Item> findAll(PageRequest pageRequest);

  Page<Item> findAll(Pageable pageable);
}
