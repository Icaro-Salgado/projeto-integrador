package br.com.mercadolivre.projetointegrador.marketplace.repository;

import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BatchRepository extends JpaRepository<Batch, Long> {
}
