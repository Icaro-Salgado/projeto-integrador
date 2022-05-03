package br.com.mercadolivre.projetointegrador.marketplace.repository;

import br.com.mercadolivre.projetointegrador.marketplace.model.AdPurchase;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdPurchaseRepository extends JpaRepository<AdPurchase, Long> {
}
