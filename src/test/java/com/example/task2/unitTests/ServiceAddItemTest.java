package com.example.task2.unitTests;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static reactor.core.publisher.Mono.when;

import com.example.task2.exception.ApiControllerException;
import com.example.task2.feature_items.entities.mongo.Item;
import com.example.task2.feature_items.models.requests.CreateItemRequest;
import com.example.task2.feature_items.repository.ItemDao;
import com.example.task2.feature_items.services.ItemServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

public class ServiceAddItemTest {

  private MockMvc mockMvc;

  @Mock private ItemDao itemDao;

  @InjectMocks private ItemServiceImpl itemService;

  @BeforeEach
  public void setUp() {
    MockitoAnnotations.initMocks(this);
    this.mockMvc = MockMvcBuilders.standaloneSetup(itemService).build();
  }

  @Test
  public void itemService_addItem_exceptionTest() {
    CreateItemRequest itemRequest = new CreateItemRequest();
    itemRequest.setName("item1");
    //    itemRequest.setName("item1");
    itemRequest.setDescription("rfvvvv");
    itemRequest.setPrice(22);
    itemRequest.setQuantity(1);

    when(itemDao.isItemExists("item1")).thenReturn(true);

    ApiControllerException apiControllerException =
        assertThrows(
            ApiControllerException.class,
            () -> {
              itemService.addItem(itemRequest);
            });

    Assertions.assertEquals(
        "Item with name item1 already exist", apiControllerException.getMessage());
  }

  @Test
  public void itemService_addItem_successTest() {
    CreateItemRequest itemRequest = new CreateItemRequest();
    itemRequest.setId("5432454");
    itemRequest.setName("newItem");
    itemRequest.setDescription("nbbc bccinedcnuc");
    itemRequest.setPrice(200);
    itemRequest.setQuantity(10);

    Item item = new Item();
    item.setId("5432454");
    item.setName("newItem");
    item.setDescription("nbbc bccinedcnuc");
    item.setPrice(200);
    item.setQuantity(10);

    when(itemDao.isItemExists("newItem")).thenReturn(false);
    //    when(itemDao.save(item)).thenReturn(item);
    when(itemDao.save(any(Item.class))).thenReturn(item);

    Item result = itemService.addItem(itemRequest);
    Assertions.assertNotNull(result);
    Assertions.assertEquals("newItem", result.getName());
  }
}
