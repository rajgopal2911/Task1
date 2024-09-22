package com.example.task2.entities;



import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;




/**
 * this class is entity to store in database
 */
@Data
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
