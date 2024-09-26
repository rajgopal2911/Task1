package com.example.task2.constants;

public class Redis {
  private Redis() {}

  public static final String REDIS_KEY = "redis_item";
  public static final String ITEM_BY_NAME = "r:task2:items:name:{0}";
  public static final String ITEM_BY_ID = "r:task2:items:id:{0}";
  public static final Integer ITEM_BY_ID_TTL = 180;
}
