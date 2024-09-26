package com.example.task2.exception;

import com.example.task2.models.ApiResponse;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<Map<String, String>> handleMethodArgumentNotVlidException(
      MethodArgumentNotValidException exp) {
    Map<String, String> response = new HashMap<>();
    exp.getBindingResult()
        .getAllErrors()
        .forEach(
            error -> {
              String fieldName = ((FieldError) error).getField();
              String message = error.getDefaultMessage();
              response.put(fieldName, message);
            });

    return new ResponseEntity<Map<String, String>>(response, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ApiControllerException.class)
  public ResponseEntity<ApiResponse> handlingAllApiExceptions(ApiControllerException exception) {
    ApiResponse apiResponse = new ApiResponse();
    apiResponse.setError(exception.getMessage());
    apiResponse.setErrorCode(exception.getErrorCode());
    apiResponse.setSuccess(false);
    return ResponseEntity.badRequest().body(apiResponse);
  }
}
