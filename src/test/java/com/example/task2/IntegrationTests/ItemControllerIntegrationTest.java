package com.example.task2.IntegrationTests;
import com.example.task2.feature_items.entities.mongo.Item;
import com.example.task2.feature_items.models.requests.CreateItemRequest;
import com.example.task2.feature_items.services.ItemServiceImpl;
import com.example.task2.feature_items.controllers.ItemControllers; // Import your controller
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MongoDBContainer;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ItemControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ItemServiceImpl itemService;

    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @DynamicPropertySource
    static void mongoProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @BeforeAll
    static void beforeAll() {
        mongoDBContainer.start();
    }

    @AfterAll
    static void afterAll() {
        mongoDBContainer.stop();
    }

    @Test
    public void createItemTest() throws Exception {
        CreateItemRequest request = new CreateItemRequest();
        request.setName("Laptop");
        request.setDescription("High-end laptop");
        request.setPrice(1200);
        request.setQuantity(5);

        // Convert the request object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonRequest = objectMapper.writeValueAsString(request);

        // Perform the POST request
        ResultActions response = mockMvc.perform(MockMvcRequestBuilders
                .post("/items/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonRequest));

        // Verify the response
        response.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.data.name").value("Laptop"))
                .andDo(result -> {
                    // Optionally, you can print the result
                    System.out.println(result.getResponse().getContentAsString());
                });

    }
}
