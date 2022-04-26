package br.com.mercadolivre.projetointegrador.marketplace.repository;

public interface CacheRepository<T, K> {

    String get(K key);
    void set(K key, T value);

}
