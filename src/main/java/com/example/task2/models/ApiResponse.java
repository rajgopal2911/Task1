package com.example.task2.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse {
    private boolean success = true;
    private String error;
    private String errorCode;
    private Object data;
}
