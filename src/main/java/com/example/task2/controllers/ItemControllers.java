package com.example.task2.controllers;


import com.example.task2.entities.Item;
import com.example.task2.models.ApiResponse;
import com.example.task2.models.requests.UpdateItem;
import com.example.task2.models.requests.UpdateQuantity;
import com.example.task2.services.ItemServicesImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/items")
@Validated
public class ItemControllers {

    @Autowired
    private ItemServicesImpl itemServices;


    /**
     * used to add item in db
     * @param item
     * @return
     */
    @PostMapping("/add")
    public ResponseEntity<Object> createItem(@Valid @RequestBody Item item) {
        ApiResponse apiResponse=new ApiResponse();
        apiResponse.setData(itemServices.addItem(item));
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }


    /**
     * used to delete item from db
     *
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id){
        ApiResponse apiResponse=new ApiResponse();
        String response= String.valueOf(itemServices.deleteItem(id));
        if(response.compareTo("Item not found") == 0){
            apiResponse.setSuccess(false);

        }

        apiResponse.setData(response);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }


    /**
     * used to get all items from db
     * @return
     */
    @GetMapping
    public ResponseEntity<ApiResponse> getAllItems(){
        ApiResponse apiResponse=new ApiResponse();

        List<Item> listOfItems = itemServices.getAllItems();
        apiResponse.setData(listOfItems);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);

    }


    /**
     * updates item name,description and price
     * @param item
     * @param id
     * @return
     */
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateItem(@Valid @RequestBody UpdateItem item, @PathVariable String id){
        return itemServices.updateItem(id,item);
    }


    /**
     * uodates only quantity of item
     * @param id
     * @param UpdateQuantity
     * @return
     */
    @PutMapping("/updateQuantity/{id}")
    public ResponseEntity<Object> updateQuantity(@PathVariable String id, @RequestBody UpdateQuantity UpdateQuantity){
        ApiResponse apiResponse=new ApiResponse();
        ResponseEntity<Object> item=itemServices.updateQuantity(id,UpdateQuantity);
        apiResponse.setData(item);
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getItemById(@PathVariable String id){
        ApiResponse apiResponse=new ApiResponse();
         Item item=itemServices.getItemById(id);
         if(item==null){
             apiResponse.setSuccess(false);
             return new ResponseEntity<>(apiResponse,HttpStatus.OK);
         }
         apiResponse.setData(item);

        return new ResponseEntity<>(apiResponse,HttpStatus.OK);

    }




}
