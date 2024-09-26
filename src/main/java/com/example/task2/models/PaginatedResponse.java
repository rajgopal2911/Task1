package com.example.task2.models;

import java.util.List;
import lombok.Data;

@Data
public class PaginatedResponse<T> {
  private Integer pageNo;
  private Integer pageSize;
  private List<T> items;
}
