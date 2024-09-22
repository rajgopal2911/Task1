package com.example.task2.models.requests;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;


/**
 * this class is used as requestbody to update items name,description and price
 */
@Data
public class UpdateItem {

    @NotEmpty(message = "name cannot be empty")
    private String name;

    @NotEmpty(message = "write descrition")
    private String description;

    @Positive(message = "price must be positive")
    private int price;
}
