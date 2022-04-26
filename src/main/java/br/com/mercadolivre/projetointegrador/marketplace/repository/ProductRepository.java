package br.com.mercadolivre.projetointegrador.marketplace.repository;

import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Product findByName(String name);

    @Query(value="SELECT * FROM Product WHERE category = ?1", nativeQuery = true)
    List<Product> findAllByCategory(String category);
}
