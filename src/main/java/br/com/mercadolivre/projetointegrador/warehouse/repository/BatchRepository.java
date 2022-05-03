package br.com.mercadolivre.projetointegrador.warehouse.repository;

import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BatchRepository extends JpaRepository<Batch, Long> {

  Optional<Batch> findByBatchNumber(Integer batch_number);

  List<Batch> findAllByBatchNumberIn(List<Integer> batchNumberList);

  List<Batch> findAllBySellerIdAndDueDateGreaterThan(Long id, LocalDate date);

  List<Batch> findAllBySection_IdIn(List<Long> ids);
}
