package br.com.mercadolivre.projetointegrador.marketplace.repository;

import br.com.mercadolivre.projetointegrador.marketplace.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
  Optional<Purchase> findByBuyerId(Long buyerId);
}
