package br.com.mercadolivre.projetointegrador.warehouse.repository;

import br.com.mercadolivre.projetointegrador.warehouse.enums.CategoryEnum;
import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.model.Product;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BatchRepository extends JpaRepository<Batch, Long> {

  Optional<Batch> findByBatchNumber(Integer batch_number);

  List<Batch> findAllByBatchNumberIn(List<Integer> batchNumberList);

  List<Batch> findAllBySellerIdAndDueDateGreaterThan(Long id, LocalDate date);

  List<Batch> findAllBySection_IdIn(List<Long> ids);

  List<Batch> findAllBySectionIdAndDueDateLessThan(Long id, LocalDate date);

  List<Batch> findBatchByProductAndSection(Product product, Section section, Sort sort);

  List<Batch> findAllByDueDateLessThanAndProductCategoryOrderByDueDate(
      LocalDate date, CategoryEnum category);

  List<Batch> findAllByDueDateLessThanAndProductCategoryOrderByDueDateDesc(
      LocalDate date, CategoryEnum category);

  List<Batch> findAllByProductId(Long id);

  List<Batch> findBatchByProductAndSection(Product product, Section section);

  List<Batch> findBatchByProductAndSectionAndDueDateGreaterThan(
      Product product, Section section, Sort sort, LocalDate date);
}
