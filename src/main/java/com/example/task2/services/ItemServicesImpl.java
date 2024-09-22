package com.example.task2.services;


import com.example.task2.dao.ItemDao;
import com.example.task2.entities.Item;
import com.example.task2.models.requests.UpdateItem;
import com.example.task2.models.requests.UpdateQuantity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Validated
public class ItemServicesImpl implements ItemServices {

    @Autowired
    private ItemDao itemDao;

    // Constructor based injection.

    /**
     * adds item in db
     *
     * @param item
     * @return
     */
    @Override
    public Object addItem(Item item){

        boolean isPresent=itemDao.nameExist(item.getName());

        if(!isPresent){
           return new String("item with name "+item.getName()+" already exist");
        }

        itemDao.saveItem(item);

        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    /**
     * delete item from db
     *
     * @param id
     * @return
     */
    @Override
    public ResponseEntity<Object> deleteItem(String id){
        return itemDao.deleteItem(id);
    }

    /**
     * returns the list of items present in db
     * @return
     */
    @Override
    public List<Item> getAllItems(){
        return itemDao.getAllItems().stream().toList();
    }


    /**
     * updates items name,description, price
     * @param id
     * @param updateItem
     * @return
     */
    @Override
    public ResponseEntity<String> updateItem(String id, UpdateItem updateItem){
        return itemDao.updateItem(id,updateItem);
    }

    /**
     * this method is yused to update quantity of items
     * @param id
     * @param UpdateQuantity
     * @return
     */
    @Override
    public ResponseEntity<Object> updateQuantity(String id ,UpdateQuantity UpdateQuantity){
        if(UpdateQuantity.getQuantity() <0){
            return ResponseEntity.ok("Quantity cant be negtive");
        }
        return itemDao.updateQuatity(id,UpdateQuantity);
    }

}
