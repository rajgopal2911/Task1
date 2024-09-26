package com.example.task2.services;

import com.example.task2.utils.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class RedisService {

  @Autowired private RedisTemplate<String, String> template;

  private final ObjectMapper objectMapper = new ObjectMapper();

  public void save(String key, Object value, Integer ttl) {
    String jsonValue = StringUtils.convertToString(value);
    if (jsonValue == null) {
      return;
    }

    template.opsForValue().set(key, jsonValue);
    template.expire(key, ttl, TimeUnit.SECONDS);
  }

  public String get(String key) {
    return template.opsForValue().get(key);
  }

  public Boolean evict(String key) {
    // Delete the key from Redis
    String item = template.opsForValue().get(key);
    if (item == null) {
      return false;
    }
    return template.delete(key);
  }
}
