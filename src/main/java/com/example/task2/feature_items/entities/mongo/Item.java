package com.example.task2.feature_items.entities.mongo;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

/** this class is entity to store in database */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document(collection = "Items")
public class Item {

  private String id;

  @NotEmpty(message = "User name cannot be empty")
  private String name;

  @NotEmpty(message = "description cannot be empty")
  private String description;

  @Positive(message = "price must be positive")
  private int price;

  @Positive(message = "quantity cannot be empty")
  private int quantity;
}
