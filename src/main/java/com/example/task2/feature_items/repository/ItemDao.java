package com.example.task2.feature_items.repository;

import com.example.task2.constants.Redis;
import com.example.task2.feature_items.entities.mongo.Item;
import com.example.task2.services.RedisService;
import com.example.task2.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.MessageFormat;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ItemDao {

  private final ItemRepository itemRepository;
  private final RedisService redisService;
  private final ObjectMapper objectMapper = new ObjectMapper();

  @Autowired
  public ItemDao(ItemRepository itemRepository, RedisService redisService) {
    this.itemRepository = itemRepository;
    this.redisService = redisService;
  }

  /**
   * Save the item
   *
   * @param item
   * @return
   */
  public Item save(Item item) {
    // Cache Evict
    String redisKey = MessageFormat.format(Redis.ITEM_BY_ID, item.getId());
    String key = redisKey;
    if (!redisService.evict(key)) {
      return itemRepository.save(item);
    }
    return itemRepository.save(item);
  }

  /**
   * Checks whether item exist or not
   *
   * @param name
   * @return
   */
  public Boolean isItemExists(String name) {
    return itemRepository.existsByName(name); // Assuming itemRepository returns boolean
  }

  /**
   * Deletes item
   *
   * @param id
   * @return
   */
  public void delete(String id) {

    itemRepository.deleteById(id);
  }

  /**
   * Finds all item from db
   *
   * @param pageNo
   * @param pageSize
   * @return
   */
  public Page<Item> findAll(Integer pageNo, Integer pageSize) {
    PageRequest pageRequest = PageRequest.of(pageNo, pageSize);
    return itemRepository.findAll(pageRequest);
  }

  /**
   * Returns elemeth for given id
   *
   * @param id
   * @return
   */
  public Optional<Item> getItemById(String id) {
    String redisKey = MessageFormat.format(Redis.ITEM_BY_ID, id);
    Item item = StringUtils.convertToObject(redisService.get(redisKey), Item.class);
    if (item != null) {
      return Optional.of(item);
    }

    Optional<Item> itemOpt = itemRepository.findById(id);
    redisService.save(redisKey, itemOpt.get(), Redis.ITEM_BY_ID_TTL);
    return itemOpt;
  }
}
