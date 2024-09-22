package com.example.task2.repository;


import com.example.task2.models.requests.UpdateItem;
import com.example.task2.models.requests.UpdateQuantity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class ItemDao {

    @Autowired
    private Item itemRepository;

    /**
     * Save the item
     * @param item
     * @return
     */
    public com.example.task2.entities.Item saveItem(com.example.task2.entities.Item item){
        return itemRepository.save(item);
    }

    public boolean nameExist(String name){
        return itemRepository.existsByName(name);
    }


    /**
     * deletes item
     * @param id
     * @return
     */
    public ResponseEntity<Object> deleteItem(String id){
        if(!itemRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item not found");
        }
        itemRepository.deleteById(id);
        return ResponseEntity.ok("Item succesfully deleted");
    }

    /**
     * returns the list of all items
     * @return
     */
    public List<com.example.task2.entities.Item> getAllItems(){

        return itemRepository.findAll();
        // return itemRepository.findAll();
    }

    /**
     *Updates items name,price,description
     * @param id
     * @param updateItem
     * @return
     */
    public ResponseEntity<String> updateItem(String id, UpdateItem updateItem){
        com.example.task2.entities.Item existingItem=itemRepository.findById(id).orElse(null);
        if(existingItem == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("itemem not present");
        }

        existingItem.setName(updateItem.getName());
        existingItem.setDescription(updateItem.getDescription());
        existingItem.setPrice(updateItem.getPrice());
        itemRepository.save(existingItem);
        return ResponseEntity.ok("Updated successfully");


    }

    /**
     * Updates Quantity of item
     * @param id
     * @param UpdateQuantity
     * @return
     */

    public ResponseEntity<Object> updateQuatity(String id,UpdateQuantity UpdateQuantity){
        if(!itemRepository.existsById(id)){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Item Not found");
        }

        com.example.task2.entities.Item existingItem=itemRepository.findById(id).get();
        int existingQuantity=existingItem.getQuantity();

        existingItem.setQuantity(UpdateQuantity.getQuantity()+existingQuantity);

        itemRepository.save(existingItem);
        return ResponseEntity.ok(existingItem);

    }
}
