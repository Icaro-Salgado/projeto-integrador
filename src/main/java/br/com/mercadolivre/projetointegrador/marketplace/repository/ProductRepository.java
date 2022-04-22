package br.com.mercadolivre.projetointegrador.marketplace.repository;

import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
