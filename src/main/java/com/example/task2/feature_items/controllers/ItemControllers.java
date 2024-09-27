package com.example.task2.feature_items.controllers;

import com.example.task2.feature_items.models.requests.CreateItemRequest;
import com.example.task2.feature_items.models.requests.UpdateItemRequest;
import com.example.task2.feature_items.models.requests.UpdateQuantityRequest;
import com.example.task2.feature_items.services.ItemServiceImpl;
import com.example.task2.models.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/items")
@Validated
public class ItemControllers {

  private final ItemServiceImpl itemServices;

  @Autowired
  public ItemControllers(ItemServiceImpl itemServices) {
    this.itemServices = itemServices;
  }

  /**
   * Used to add item in db
   *
   * @param itemRequest
   * @return
   */
  @PostMapping("/add")
  public ResponseEntity<ApiResponse> createItem(@Valid @RequestBody CreateItemRequest itemRequest) {
    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setData(itemServices.addItem(itemRequest));
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  /**
   * Used to delete item from db
   *
   * @param id
   * @return
   */
  @DeleteMapping("/delete")
  public ResponseEntity<ApiResponse> delete(@RequestParam String id) {
    ApiResponse apiResponse = new ApiResponse();
    String response = String.valueOf(itemServices.deleteItem(id));
    apiResponse.setData(response);
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  /**
   * Used to get all items from db
   *
   * @return
   */
  @GetMapping
  public ResponseEntity<ApiResponse> getAllItems(
      @RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
      @RequestParam(value = "pageSize", defaultValue = "0") int pageSize) {
    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setData(itemServices.search(pageNo, pageSize));
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  /**
   * Updates item name,description and price
   *
   * @param item
   * @param id
   * @return
   */
  @PutMapping("/update/{id}")
  public ResponseEntity<ApiResponse> updateItem(
      @Valid @RequestBody UpdateItemRequest item, @PathVariable String id) {
    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setData(itemServices.updateItem(id, item));
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  /**
   * Uodates only quantity of item
   *
   * @param id
   * @param UpdateQuantity
   * @return
   */
  @PutMapping("/updateQuantity/{id}")
  public ResponseEntity<ApiResponse> updateQuantity(
      @PathVariable String id, @RequestBody UpdateQuantityRequest UpdateQuantity) {
    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setData(itemServices.updateQuantity(id, UpdateQuantity));
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }

  /**
   * Get element by Id
   *
   * @param id
   * @return
   */
  @GetMapping("/{id}")
  public ResponseEntity<ApiResponse> getItemById(@PathVariable String id) {
    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setData(itemServices.getItemById(id));
    return new ResponseEntity<>(apiResponse, HttpStatus.OK);
  }
}
