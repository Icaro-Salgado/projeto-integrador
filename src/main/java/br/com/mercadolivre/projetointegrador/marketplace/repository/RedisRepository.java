package br.com.mercadolivre.projetointegrador.marketplace.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

@AllArgsConstructor
@Repository
public class RedisRepository {

  Jedis jedis;

  public String get(String key) {
    return jedis.get(key);
  }

  public void set(String key, String value) {
    jedis.set(key, value);
  }

  public void setEx(String key, Long ttl, String value) {
    jedis.setex(key, ttl, value);
  }

  public void del(String key) { jedis.del(key); }
}
