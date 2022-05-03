package br.com.mercadolivre.projetointegrador.unit.service;

import br.com.mercadolivre.projetointegrador.security.model.AppUser;
import br.com.mercadolivre.projetointegrador.test_utils.WarehouseTestUtils;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.NotFoundException;
import br.com.mercadolivre.projetointegrador.warehouse.exception.db.ProductAlreadyExists;
import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.model.Product;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.repository.BatchRepository;
import br.com.mercadolivre.projetointegrador.warehouse.repository.ProductRepository;
import br.com.mercadolivre.projetointegrador.warehouse.service.BatchService;
import br.com.mercadolivre.projetointegrador.warehouse.service.ProductService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BatchServiceTests {

  ProductRepository productRepository = Mockito.mock(ProductRepository.class);

  BatchRepository batchRepository = Mockito.mock(BatchRepository.class);

  ProductService productService = new ProductService(productRepository);

  BatchService batchService = new BatchService(batchRepository, productService);

  @BeforeAll
  public void setUp() {
    ReflectionTestUtils.setField(batchService, "minimumWeeksToAnnounce", 3);
  }

  @Test
  @DisplayName(
      "Given a valid batch with existing product, when call createBatch, then batchRepository.save"
          + " should be called once;")
  public void createBatchSuccessfully() throws NotFoundException, ProductAlreadyExists {
    Product product = new Product();
    product.setId(1L);
    Mockito.when(productRepository.findById(Mockito.any())).thenReturn(Optional.of(product));

    Batch batch = new Batch();
    batch.setProduct(product);

    batchService.createBatch(batch);

    Mockito.verify(batchRepository, Mockito.times(1)).save(batch);
  }

  @Test
  @DisplayName(
      "Given a batch with non existing product, when call createBatch, then throws an"
          + " NotFoundException(\"Produto não encontrado.\");")
  public void failsToCreateBatchWithNonExistingProduct() {
    Product product = new Product();
    product.setId(1L);

    Batch batch = new Batch();
    batch.setProduct(product);

    Mockito.when(productRepository.findById(Mockito.any())).thenReturn(Optional.empty());

    NotFoundException thrown =
        Assertions.assertThrows(NotFoundException.class, () -> batchService.createBatch(batch));

    Assertions.assertEquals("Produto " + 1 + " não encontrado.", thrown.getMessage());
  }

  @Test
  @DisplayName(
      "Given a list of batches containing 3 batches, when call findAll, then should return size"
          + " equals 3")
  public void listAllExistingBatches() {
    List<Batch> batches = List.of(new Batch(), new Batch(), new Batch());

    Mockito.when(batchRepository.findAll()).thenReturn(batches);

    List<Batch> existingBatches = batchService.findAll();
    Assertions.assertEquals(3, existingBatches.size());
  }

  @Test
  @DisplayName("Given an ID with existing batch, when call findById, then should return this one")
  public void findExistingBatchById() throws NotFoundException {
    Batch batch = new Batch();
    batch.setPrice(BigDecimal.ONE);
    Mockito.when(batchRepository.findById(1L)).thenReturn(Optional.of(batch));

    Batch foundBatch = batchService.findById(1L);

    Assertions.assertEquals(BigDecimal.ONE, foundBatch.getPrice());
  }

  @Test
  @DisplayName(
      "Given an ID to non existing batch, when call findById, then throws an"
          + " NotFoundException(\"Lote não encontrado\");")
  public void throwsToNonExistingBatch() {
    Mockito.when(batchRepository.findById(Mockito.any())).thenReturn(Optional.empty());

    NotFoundException thrown =
        Assertions.assertThrows(NotFoundException.class, () -> batchService.findById(1L));

    Assertions.assertEquals("Lote não encontrado", thrown.getMessage());
  }

  @Test
  @DisplayName("Given an existing Batch, when call updateBatch, then should update all properties")
  public void updateExistingBatch() throws NotFoundException {
    Batch batch = new Batch();
    Mockito.when(batchRepository.findById(1L)).thenReturn(Optional.of(batch));

    batch.setSeller(new AppUser());
    batch.setSection(new Section());
    batch.setPrice(BigDecimal.valueOf(33.0));
    batch.setOrder_number(2);
    batch.setBatchNumber(2);
    batch.setQuantity(250);
    batch.setManufacturing_datetime(LocalDate.parse("2022-01-01"));
    batch.setDueDate(LocalDate.parse("2022-05-02"));

    batchService.updateBatch(1L, batch);

    Mockito.verify(batchRepository, Mockito.times(1)).save(batch);
  }

  @Test
  @DisplayName(
      "Given a non existing batch, when call updateBatch, then throws an NotFoundException(\"Lote"
          + " não encontrado\");")
  public void throwsWhenUpdateNonExistingBatch() {
    Batch updatedBatch = new Batch();
    Mockito.when(batchRepository.findById(1L)).thenReturn(Optional.empty());

    NotFoundException thrown =
        Assertions.assertThrows(
            NotFoundException.class, () -> batchService.updateBatch(1L, updatedBatch));

    Assertions.assertEquals("Lote não encontrado", thrown.getMessage());
  }

  @Test
  @DisplayName(
      "Given a existing batch, when call deleteBatch, then should call batchRepository.delete once"
          + " with this batch")
  public void deleteBatchById() throws NotFoundException {
    Batch batch = new Batch();
    Mockito.when(batchRepository.findById(1L)).thenReturn(Optional.of(batch));

    batchService.delete(1L);

    Mockito.verify(batchRepository, Mockito.times(1)).delete(batch);
  }

  @Test
  @DisplayName(
      "Given a non existing batch, when call deleteBatch, then throws an NotFoundException(\"Lote"
          + " não encontrado\");")
  public void throwsWhenDeleteNonExistingBatch() {
    Mockito.when(batchRepository.findById(1L)).thenReturn(Optional.empty());

    NotFoundException thrown =
        Assertions.assertThrows(NotFoundException.class, () -> batchService.delete(1L));

    Assertions.assertEquals("Lote não encontrado", thrown.getMessage());
  }

  @Test
  @DisplayName("Given a seller id, should return all relate batches")
  public void shouldReturnListOfBatches() {
    List<Batch> batchList = WarehouseTestUtils.getBatch();
    Mockito.when(
            batchRepository.findAllBySellerIdAndDueDateGreaterThan(
                Mockito.anyLong(), Mockito.any()))
        .thenReturn(batchList);

    List<Batch> result = batchService.listBySellerId(1L);

    Assertions.assertEquals(batchList, result);
  }
}
