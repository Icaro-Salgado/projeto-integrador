package br.com.mercadolivre.projetointegrador.marketplace.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

@AllArgsConstructor
@Repository
public class RedisRepository implements CacheRepository<String, String> {

  Jedis jedis;

  @Override
  public String get(String key) {
    return jedis.get(key);
  }

  @Override
  public void set(String key, String value) {
    jedis.set(key, value);
  }

  public void setEx(String key, Long ttl, String value) {
    jedis.setex(key, ttl, value);
  }
}
