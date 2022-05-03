package br.com.mercadolivre.projetointegrador.marketplace.repository;

import br.com.mercadolivre.projetointegrador.marketplace.model.AdPurchase;
import br.com.mercadolivre.projetointegrador.marketplace.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdPurchaseRepository extends JpaRepository<AdPurchase, Long> {
  List<AdPurchase> findAllByPurchase(Purchase purchase);
}
