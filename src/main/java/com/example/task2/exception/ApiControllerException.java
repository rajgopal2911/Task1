package com.example.task2.exception;

import lombok.Data;

@Data
public class ApiControllerException extends RuntimeException {
  private final String errorCode;

  /**
   * Initialize instance of API Controller Exception
   *
   * @param errorMessage the error Message
   * @param errorCode the error Code
   */
  public ApiControllerException(String errorMessage, String errorCode) {
    super(errorMessage);
    this.errorCode = errorCode;
  }
}
