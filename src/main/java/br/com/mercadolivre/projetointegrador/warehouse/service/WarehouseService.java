package br.com.mercadolivre.projetointegrador.warehouse.service;

import br.com.mercadolivre.projetointegrador.warehouse.enums.CategoryEnum;
import br.com.mercadolivre.projetointegrador.warehouse.enums.SortTypeEnum;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.NotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.SectionNotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.model.*;
import br.com.mercadolivre.projetointegrador.warehouse.repository.BatchRepository;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.WarehouseNotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;
import br.com.mercadolivre.projetointegrador.warehouse.repository.WarehouseRepository;
import br.com.mercadolivre.projetointegrador.warehouse.service.validators.BatchDuplicatedValidator;
import br.com.mercadolivre.projetointegrador.warehouse.service.validators.WarehouseValidatorExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WarehouseService {

  private final WarehouseRepository warehouseRepository;
  private final BatchService batchService;
  private final BatchRepository batchRepository;
  private final WarehouseValidatorExecutor warehouseValidatorExecutor;
  private final SectionRepository sectionRepository;
  private final ProductService productService;

  public Warehouse createWarehouse(Warehouse warehouse) {
    return warehouseRepository.save(warehouse);
  }

  public Warehouse findWarehouse(final Long id) {
    return warehouseRepository
        .findById(id)
        .orElseThrow(() -> new WarehouseNotFoundException("Warehouse não encontrada."));
  }

  public List<Batch> saveBatchInSection(InboundOrder inboundOrder) throws NotFoundException {

    warehouseValidatorExecutor.executeValidators(
        inboundOrder, List.of(new BatchDuplicatedValidator(inboundOrder, batchRepository)));

    List<Batch> addedBatches = new ArrayList<>();

    for (Batch batch : inboundOrder.getBatches()) {
      addedBatches.add(batch);
      batchService.createBatch(batch);
    }

    return addedBatches;
  }

  public List<Batch> updateBatchInSection(InboundOrder inboundOrder) throws NotFoundException {
    warehouseValidatorExecutor.executeValidators(inboundOrder);
    List<Batch> addedBatches = new ArrayList<>();

    for (Batch batch : inboundOrder.getBatches()) {
      addedBatches.add(batchService.updateBatchByBatchNumber(batch));
    }
    return addedBatches;
  }

  public List<Batch> dueDateBatches(Long numberOfDays, Long sectionId) {

    List<Batch> section =
        batchRepository.findAllBySectionIdAndDueDateLessThan(
            sectionId, LocalDate.now().plusDays(numberOfDays));

    return section;
  }

  public List<Batch> dueDateBatchesByCategory(
      Long numberofdays, CategoryEnum category, String order) {

    if (order.equals("ASC")) {
      return batchRepository.findAllByDueDateLessThanAndProductCategoryOrderByDueDate(
          LocalDate.now().plusDays(numberofdays), category);
    }
    if (order.equals("DESC")) {
      return batchRepository.findAllByDueDateLessThanAndProductCategoryOrderByDueDateDesc(
          LocalDate.now().plusDays(numberofdays), category);
    }
    throw new IllegalArgumentException("Informe o seletor de ordenação (ASC ou DESC)");
  }

  public List<Batch> findProductOnManagerSection(
      Long managerId, Long productId, SortTypeEnum sortType) throws RuntimeException {
    Section managerSection =
        sectionRepository
            .findByManagerId(managerId)
            .orElseThrow(
                (() ->
                    new SectionNotFoundException(
                        "Não foi encontrada nenhuma seção vinculada ao usuário")));

    Product product = productService.findById(productId);
    return batchService.findBatchesByProductAndSection(
        product, managerSection, Sort.by(Sort.Direction.ASC, sortType.field));
  }
}
