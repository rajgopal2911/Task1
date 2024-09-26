package com.example.task2.feature_items.models.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/** this class is used to just update quantity of item */
@Data
public class UpdateQuantityRequest {

  @NotEmpty
  @Positive
  @Min(value = 1, message = "value must be greater than zero")
  private int quantity;
}
