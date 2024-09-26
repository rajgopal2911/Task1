package com.example.task2.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StringUtils {

  private StringUtils() {}

  private static final ObjectMapper objectMapper = new ObjectMapper();

  public static String convertToString(Object input) {
    if (input == null) {
      return null;
    }

    try {
      return objectMapper.writeValueAsString(input);
    } catch (Exception e) {
      // Handle the exception or rethrow it based on your use case
      return null;
    }
  }

  public static <T> T convertToObject(String message, Class<T> classType) {
    try {
      return objectMapper.readValue(message, classType);
    } catch (Exception e) {
      System.out.println(e);
      return null;
    }
  }
}
