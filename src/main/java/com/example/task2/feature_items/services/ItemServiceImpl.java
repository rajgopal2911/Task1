package com.example.task2.feature_items.services;

import static com.example.task2.constants.ErrorCodes.GENERIC_ERROR;
import static com.example.task2.constants.ErrorMessages.BEING_USED;
import static com.example.task2.constants.ErrorMessages.ITEM_ALREADY_EXIST_MESSAGE;
import static com.example.task2.constants.ErrorMessages.ITEM_NOT_FOUND_ERROR_MESSAGE;

import com.example.task2.constants.Redis;
import com.example.task2.exception.ApiControllerException;
import com.example.task2.feature_items.entities.mongo.Item;
import com.example.task2.feature_items.models.requests.CreateItemRequest;
import com.example.task2.feature_items.models.requests.UpdateItemRequest;
import com.example.task2.feature_items.models.requests.UpdateQuantityRequest;
import com.example.task2.feature_items.models.response.ItemResponse;
import com.example.task2.feature_items.repository.ItemDao;
import com.example.task2.models.PaginatedResponse;
import com.example.task2.services.ResourceLocking;
import java.text.MessageFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemServiceImpl implements ItemServices {

  /*
  TODO:
  1. Implement Constructor injection-done
  2. Return Custom Response Object, do not return response entity.->done
  3. Throw Exceptions when necessary.->done
  4. Create a paginated response model->done
  5. use message format instead of regular string-done
  6. Create Class ErrorMessage and ErrorCode. Borrow error message from this constants class.
   */

  private final ItemDao itemDao;
  private final ResourceLocking resourceLocking;

  @Autowired
  public ItemServiceImpl(ItemDao itemDao, ResourceLocking resourceLocking) {
    this.itemDao = itemDao;
    this.resourceLocking = resourceLocking;
  }

  /**
   * Adds item in db
   *
   * @param itemRequest
   * @return
   */
  @Override
  public Item addItem(CreateItemRequest itemRequest) {
    if (itemDao.isItemExists(itemRequest.getName())) {
      throw new ApiControllerException(
          MessageFormat.format(ITEM_ALREADY_EXIST_MESSAGE, itemRequest.getName()), GENERIC_ERROR);
    }
    Item item = convertToItem(itemRequest);

    return itemDao.save(item);
  }

  /**
   * Deletes item of given id
   *
   * @param id
   * @return
   */
  @Override
  public String deleteItem(String id) {
    Item item = itemDao.getItemById(id).orElse(null);
    if (item == null) {
      throw new ApiControllerException(
          MessageFormat.format(ITEM_NOT_FOUND_ERROR_MESSAGE, id), GENERIC_ERROR);
    }
    itemDao.delete(id);
    return MessageFormat.format("Item with id {0} is deleted successfully", id);
  }

  /**
   * Returns all values present in it
   *
   * @param pageNo
   * @param pageSize
   * @return
   */
  @Override
  public PaginatedResponse<ItemResponse> search(Integer pageNo, Integer pageSize) {
    PaginatedResponse<ItemResponse> paginatedResponse = new PaginatedResponse<>();
    paginatedResponse.setPageSize(pageSize);
    paginatedResponse.setPageNo(pageNo);
    paginatedResponse.setItems(
        itemDao.findAll(pageNo, pageSize).stream().map(this::prepareItemResponse).toList());
    return paginatedResponse;
  }

  /**
   * Updates items name,description, price
   *
   * @param id
   * @param updateItem
   * @return
   */
  @Override
  public ItemResponse updateItem(String id, UpdateItemRequest updateItem) {
    String lockValue = String.valueOf(System.currentTimeMillis()); // Unique lock value
    try {
      boolean lockAcquired = resourceLocking.acquireLock(id, lockValue, Redis.ITEM_BY_ID_TTL);
      if (!lockAcquired) {
        throw new ApiControllerException(MessageFormat.format(BEING_USED, id), GENERIC_ERROR);
      }

      Item existingItem = itemDao.getItemById(id).orElse(null);
      if (existingItem == null) {
        throw new ApiControllerException(
            MessageFormat.format(ITEM_NOT_FOUND_ERROR_MESSAGE, id), GENERIC_ERROR);
      }
      existingItem.setName(updateItem.getName());
      existingItem.setDescription(updateItem.getDescription());
      existingItem.setPrice(updateItem.getPrice());
      return prepareItemResponse(itemDao.save(existingItem));
    } finally {
      resourceLocking.releaseLock(id, lockValue);
    }
  }

  /**
   * This method is yused to update quantity of items
   *
   * @param id
   * @param quantity
   * @return
   */
  @Override
  public ItemResponse updateQuantity(String id, UpdateQuantityRequest quantity) {
    Item existingItem = itemDao.getItemById(id).orElse(null);
    if (existingItem == null) {
      throw new ApiControllerException(
          MessageFormat.format(ITEM_NOT_FOUND_ERROR_MESSAGE, id), GENERIC_ERROR);
    }
    existingItem.setQuantity(quantity.getQuantity() + existingItem.getQuantity());
    return prepareItemResponse(itemDao.save(existingItem));
  }

  /**
   * Gets element by its id
   *
   * @param id
   * @return
   */
  @Override
  public ItemResponse getItemById(String id) {
    //    String key= RedisKey.Redis_Key;
    Item item = itemDao.getItemById(id).orElse(null);
    if (item == null) {
      throw new ApiControllerException(
          MessageFormat.format("Item with id {0} does not exist", id), "1000");
    }
    return prepareItemResponse(item);
  }

  /**
   * Prepares Item response
   *
   * @param item
   * @return
   */
  private ItemResponse prepareItemResponse(Item item) {
    ItemResponse itemResponse = new ItemResponse();
    itemResponse.setDescription(item.getDescription());
    itemResponse.setName(item.getName());
    itemResponse.setPrice(item.getPrice());
    itemResponse.setQuantity(item.getQuantity());
    return itemResponse;
  }

  /**
   * Converts itemrequest into an item entity
   *
   * @param itemRequest
   * @return
   */
  private Item convertToItem(CreateItemRequest itemRequest) {
    Item item = new Item();
    item.setName(itemRequest.getName());
    item.setDescription(itemRequest.getDescription());
    item.setPrice(itemRequest.getPrice());
    item.setQuantity(itemRequest.getQuantity());
    return item;
  }
}
