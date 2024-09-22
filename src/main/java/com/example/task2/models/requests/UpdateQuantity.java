package com.example.task2.models.requests;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import lombok.Data;



/**
 * this class is used to just update quantity of item
 */
@Data
public class UpdateQuantity {

    @NotEmpty
    @Positive
    @Min(value = 1,message = "value must be greater than zero")
    private int quantity;
}
