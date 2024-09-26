package com.example.task2.feature_items.models.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateItemRequest {
  private String id;
  private String name;
  private String description;
  private int price;
  private int quantity;
}
