package com.example.task2.services;

import java.text.MessageFormat;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class ResourceLocking {
  public static final String LOCK_KEY = "r:lock:{0}";

  private final RedisTemplate<String, String> redisTemplate;

  @Autowired
  public ResourceLocking(RedisTemplate<String, String> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  public Boolean acquireLock(String id, String lockValue, Integer ttl) {
    String lockKey = MessageFormat.format(LOCK_KEY, id);
    return redisTemplate.opsForValue().setIfAbsent(lockKey, lockValue, ttl, TimeUnit.SECONDS);
  }

  public void releaseLock(String id, String lockValue) {
    String lockKey = MessageFormat.format(LOCK_KEY, id);
    String currentValue = redisTemplate.opsForValue().get(lockKey);
    if (currentValue == null) {
      return;
    }
    redisTemplate.delete(lockKey);
  }
}
