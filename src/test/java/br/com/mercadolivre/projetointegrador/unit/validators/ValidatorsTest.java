package br.com.mercadolivre.projetointegrador.unit.validators;

import br.com.mercadolivre.projetointegrador.warehouse.enums.CategoryEnum;
import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.repository.BatchRepository;
import br.com.mercadolivre.projetointegrador.test_utils.WarehouseTestUtils;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.*;
import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;
import br.com.mercadolivre.projetointegrador.warehouse.repository.WarehouseRepository;
import br.com.mercadolivre.projetointegrador.warehouse.service.validators.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class ValidatorsTest {

  @Mock private WarehouseRepository warehouseRepository;

  @Mock private SectionRepository sectionRepository;

  @Mock private BatchRepository batchRepository;

  @Test
  public void shouldThrowExceptionWhenNotFoundWarehouse() {
    WarehouseExistsValidator validator = new WarehouseExistsValidator(1L, warehouseRepository);

    assertThrows(WarehouseNotFoundException.class, validator::Validate);
  }

  @Test
  public void shouldNotThrowExceptionWhenFoundWarehouse() {
    Mockito.when(warehouseRepository.findById(Mockito.any()))
        .thenReturn(Optional.of(new Warehouse()));
    WarehouseExistsValidator validator = new WarehouseExistsValidator(1L, warehouseRepository);

    assertDoesNotThrow(validator::Validate);
  }

  @Test
  public void shouldThrowExceptionWhenNotFoundSection() {
    SectionExistsValidator validator = new SectionExistsValidator(1L, sectionRepository);

    assertThrows(SectionNotFoundException.class, validator::Validate);
  }

  @Test
  public void shouldNotThrowExceptionWhenFoundSection() {
    Mockito.when(sectionRepository.findById(Mockito.any())).thenReturn(Optional.of(new Section()));
    SectionExistsValidator validator = new SectionExistsValidator(1L, sectionRepository);

    assertDoesNotThrow(validator::Validate);
  }

  @Test
  public void shouldThrowExceptionInboundOrderHasMoreThanSectionLimit() {
    Mockito.when(sectionRepository.findById(Mockito.any()))
        .thenReturn(Optional.of(Section.builder().capacity(1).build()));
    Mockito.when(batchRepository.findAllBySection_IdIn(Mockito.any()))
        .thenReturn(Collections.nCopies(2, new Batch()));

    InboundOrder inboundOrder = WarehouseTestUtils.getInboundOrder();

    SectionCapacityValidator validator =
        new SectionCapacityValidator(inboundOrder, sectionRepository, batchRepository);

    assertThrows(SectionTotalCapacityException.class, validator::Validate);
  }

  @Test
  public void shouldNotThrowExceptionWhenInboundOrderHasLessThanSectionLimit() {
    Mockito.when(sectionRepository.findById(Mockito.any()))
        .thenReturn(Optional.of(Section.builder().capacity(1000).build()));
    Mockito.when(batchRepository.findAllBySection_IdIn(Mockito.any()))
        .thenReturn(Collections.emptyList());
    InboundOrder inboundOrder = WarehouseTestUtils.getInboundOrder();

    SectionCapacityValidator validator =
        new SectionCapacityValidator(inboundOrder, sectionRepository, batchRepository);

    assertDoesNotThrow(validator::Validate);
  }

  @Test
  public void shouldThrowExceptionWhenBatchCategoryIsDiffThanSection() {
    Mockito.when(sectionRepository.findById(Mockito.any()))
        .thenReturn(Optional.of(Section.builder().product_category(CategoryEnum.FF).build()));
    InboundOrder inboundOrder = WarehouseTestUtils.getInboundOrder();

    SectionAndProductMatchValidator validator =
        new SectionAndProductMatchValidator(inboundOrder, sectionRepository);

    assertThrows(SectionDoesNotMatchWithProductException.class, validator::Validate);
  }

  @Test
  public void shouldNotThrowExceptionWhenBatchCategoryIsEqualWithSection() {
    Mockito.when(sectionRepository.findById(Mockito.any()))
        .thenReturn(Optional.of(Section.builder().product_category(CategoryEnum.FS).build()));
    InboundOrder inboundOrder = WarehouseTestUtils.getInboundOrder();

    SectionAndProductMatchValidator validator =
        new SectionAndProductMatchValidator(inboundOrder, sectionRepository);

    assertDoesNotThrow(validator::Validate);
  }

  @Test
  public void shouldThrowExceptionWhenHasDuplicatedBatch() {
    Mockito.when(batchRepository.findAllByBatchNumberIn(Mockito.any()))
        .thenReturn(List.of(new Batch()));
    InboundOrder inboundOrder = WarehouseTestUtils.getInboundOrder();

    BatchDuplicatedValidator validator =
        new BatchDuplicatedValidator(inboundOrder, batchRepository);

    assertThrows(BatchAlreadyExists.class, validator::Validate);
  }

  @Test
  public void shouldNotThrowExceptionWhenNotFindDuplicatedBatch() {
    Mockito.when(batchRepository.findAllByBatchNumberIn(Mockito.any()))
        .thenReturn(Collections.emptyList());
    InboundOrder inboundOrder = WarehouseTestUtils.getInboundOrder();

    BatchDuplicatedValidator validator =
        new BatchDuplicatedValidator(inboundOrder, batchRepository);

    assertDoesNotThrow(validator::Validate);
  }
}
