package com.example.task2.feature_items.services;

import com.example.task2.feature_items.models.requests.CreateItemRequest;
import com.example.task2.feature_items.models.requests.UpdateItemRequest;
import com.example.task2.feature_items.models.requests.UpdateQuantityRequest;
import com.example.task2.feature_items.models.response.ItemResponse;
import com.example.task2.models.PaginatedResponse;

public interface ItemServices {

  /**
   * adds item in db
   *
   * @param createItemRequest
   * @return
   */
  Object addItem(CreateItemRequest createItemRequest);

  /**
   * deletes item from db
   *
   * @param id
   * @return
   */
  String deleteItem(String id);

  /**
   * returns all items from db
   *
   * @return
   */
  //    List<Item> getAllItems();

  /**
   * @param pageNo
   * @param pageSize
   * @return
   */
  PaginatedResponse<ItemResponse> search(Integer pageNo, Integer pageSize);

  /**
   * updates items name,description nad price
   *
   * @param id
   * @param updateItem
   * @return
   */
  ItemResponse updateItem(String id, UpdateItemRequest updateItem);

  /**
   * updates items quantity
   *
   * @param id
   * @param updateQuantity
   * @return
   */
  ItemResponse updateQuantity(String id, UpdateQuantityRequest updateQuantity);

  /**
   * @param id
   * @return
   */
  ItemResponse getItemById(String id);
}
