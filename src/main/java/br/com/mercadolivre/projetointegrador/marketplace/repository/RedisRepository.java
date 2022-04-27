package br.com.mercadolivre.projetointegrador.marketplace.repository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;

@Repository
@AllArgsConstructor
public class RedisRepository implements CacheRepository<String, String> {

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

}
