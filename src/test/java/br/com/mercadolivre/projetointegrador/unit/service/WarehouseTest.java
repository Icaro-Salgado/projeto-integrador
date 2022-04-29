package br.com.mercadolivre.projetointegrador.unit.service;

import br.com.mercadolivre.projetointegrador.warehouse.enums.SortTypeEnum;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.NotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.SectionNotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.model.Product;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.service.BatchService;
import br.com.mercadolivre.projetointegrador.test_utils.WarehouseTestUtils;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;
import br.com.mercadolivre.projetointegrador.warehouse.service.ProductService;
import br.com.mercadolivre.projetointegrador.warehouse.service.WarehouseService;
import br.com.mercadolivre.projetointegrador.warehouse.service.validators.WarehouseValidatorExecutor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class WarehouseTest {

  @Mock private BatchService batchService;

  @Mock private SectionRepository sectionRepository;

  @Mock private WarehouseValidatorExecutor warehouseValidatorExecutor;

  @Mock private ProductService productService;

  @InjectMocks private WarehouseService warehouseService;

  @Test
  public void TestIfSaveBatchInSection() throws NotFoundException {

    List<Batch> expected = WarehouseTestUtils.getBatch();

    Mockito.doNothing().when(batchService).createBatch(Mockito.any());

    List<Batch> result = warehouseService.saveBatchInSection(WarehouseTestUtils.getInboundOrder());
    assertEquals(expected, result);
  }

  @Test
  public void TestIfupdateBatchInSection() throws NotFoundException {

    List<Batch> expected = List.of(WarehouseTestUtils.getBatch1(), WarehouseTestUtils.getBatch2());

    Mockito.when(batchService.updateBatchByBatchNumber(Mockito.any()))
        .thenAnswer(i -> i.getArgument(0));

    List<Batch> result =
        warehouseService.updateBatchInSection(WarehouseTestUtils.getInboundOrder());

    assertEquals(expected, result);
  }

  @Test
  public void TestIfFindBatchesOnManagerSection() {
    Section expectedSection = new Section();
    Product expectedProduct = new Product();
    Mockito.when(sectionRepository.findByManagerId(Mockito.anyLong()))
        .thenReturn(Optional.of(expectedSection));
    Mockito.when(productService.findById(Mockito.anyLong())).thenReturn(expectedProduct);

    assertDoesNotThrow(() -> warehouseService.findProductOnManagerSection(1L, 1L, SortTypeEnum.C));
    Mockito.verify(batchService, Mockito.times(1))
        .findBatchesByProductAndSection(Mockito.eq(expectedProduct), Mockito.eq(expectedSection), Mockito.any());
  }

  @Test
  public void TestIfFindBatchesOnManagerSectionThrowsError() {
    Mockito.when(sectionRepository.findByManagerId(Mockito.anyLong())).thenReturn(Optional.empty());

    assertThrows(
        SectionNotFoundException.class,
        () -> warehouseService.findProductOnManagerSection(1L, 1L, SortTypeEnum.C));
  }
}
