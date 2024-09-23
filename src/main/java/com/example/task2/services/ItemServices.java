package com.example.task2.services;

import com.example.task2.entities.Item;
import com.example.task2.models.requests.UpdateItem;
import com.example.task2.models.requests.UpdateQuantity;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ItemServices {

    /**
     * adds item in db
     *
     * @param item
     * @return
     */
    Object addItem(Item item);

    /**
     * deletes item from db
     *
     * @param id
     * @return
     */
    ResponseEntity<Object> deleteItem(String id);

    /**
     * returns all items from db
     * @return
     */
    List<Item> getAllItems();

    /**
     * updates items name,description nad price
     * @param id
     * @param updateItem
     * @return
     */
    ResponseEntity<String> updateItem(String id, UpdateItem updateItem);

    /**
     * updates items quantity
     * @param id
     * @param updateQuantity
     * @return
     */
    ResponseEntity<Object> updateQuantity(String id, UpdateQuantity updateQuantity);

    Item getItemById(String id);
}
