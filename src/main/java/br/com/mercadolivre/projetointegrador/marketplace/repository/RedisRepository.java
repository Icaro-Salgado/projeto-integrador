package br.com.mercadolivre.projetointegrador.marketplace.repository;

import redis.clients.jedis.Jedis;

public class RedisRepository implements CacheRepository<String, String> {

    Jedis jedis;

    @Override
    public String get(String key) {
        return null;
    }

    @Override
    public void set(String key, String value) {

    }
}
