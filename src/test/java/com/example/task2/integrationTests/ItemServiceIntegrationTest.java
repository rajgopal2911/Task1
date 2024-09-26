package com.example.task2.integrationTests;

import com.example.task2.feature_items.entities.mongo.Item;
import com.example.task2.feature_items.models.requests.CreateItemRequest;
import com.example.task2.feature_items.services.ItemServiceImpl;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.testcontainers.containers.MongoDBContainer;

import static com.mongodb.assertions.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ItemServiceIntegrationTest {

  @Autowired
  private MockMvc mockMvc;
  private final ItemServiceImpl itemService;


  static MongoDBContainer mongoDBContainer=new MongoDBContainer("mongo:latest");
//          .withExposedPorts(27018);

  @DynamicPropertySource
  static void mongoProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);

  }

  @BeforeAll
  static  void beforeAll(){
    mongoDBContainer.start();
  }

//  @AfterAll
//  static void afterAll(){
//    mongoDBContainer.stop();
//  }

  @Autowired
  public ItemServiceIntegrationTest(ItemServiceImpl itemService){
    this.itemService=itemService;
  }


  @Before
  public void setUp(){
    this.mockMvc= MockMvcBuilders
            .standaloneSetup()
            .build();
  }

  @Test
  public  void addItemIntegrationTest(){
    CreateItemRequest request = new CreateItemRequest();
    request.setName("Laptop");
    request.setDescription("High-end laptop");
    request.setPrice(1200);
    request.setQuantity(5);

    Item savedItem = itemService.addItem(request);

    assertNotNull(savedItem);
    assertEquals("Laptop", savedItem.getName());

  }
}
