package com.example.task2.feature_items.models.response;

import lombok.Data;

@Data
public class ItemResponse {
  private String name;
  private String description;
  private int price;
  private int quantity;
}
