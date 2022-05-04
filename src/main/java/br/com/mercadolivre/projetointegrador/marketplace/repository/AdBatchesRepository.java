package br.com.mercadolivre.projetointegrador.marketplace.repository;

import br.com.mercadolivre.projetointegrador.marketplace.model.Ad;
import br.com.mercadolivre.projetointegrador.marketplace.model.AdBatch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdBatchesRepository extends JpaRepository<AdBatch, Long> {
  List<AdBatch> findAllByAd(Ad ad);
}
