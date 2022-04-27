package br.com.mercadolivre.projetointegrador.unit.service;

import br.com.mercadolivre.projetointegrador.marketplace.exception.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.exception.ProductAlreadyExists;
import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
import br.com.mercadolivre.projetointegrador.marketplace.repository.BatchRepository;
import br.com.mercadolivre.projetointegrador.marketplace.repository.ProductRepository;
import br.com.mercadolivre.projetointegrador.marketplace.service.BatchService;
import br.com.mercadolivre.projetointegrador.marketplace.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BatchServiceTests {

    ProductRepository productRepository = Mockito.mock(ProductRepository.class);

    BatchRepository batchRepository = Mockito.mock(BatchRepository.class);

    ProductService productService = new ProductService(productRepository);

    BatchService batchService = new BatchService(batchRepository, productService);

    @Test
    @DisplayName("Given a valid batch with existing product, when call createBatch, then batchRepository.save should be called once;")
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
    @DisplayName("Given a batch with non existing product, when call createBatch, then throws an NotFoundException(\"Produto não encontrado.\");")
    public void failsToCreateBatchWithNonExistingProduct() {
        Product product = new Product();
        product.setId(1L);

        Batch batch = new Batch();
        batch.setProduct(product);

        Mockito.when(productRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        NotFoundException thrown = Assertions.assertThrows(
                NotFoundException.class,
                () ->  batchService.createBatch(batch)
        );

        Assertions.assertEquals("Produto não encontrado.", thrown.getMessage());
    }

    @Test
    @DisplayName("Given a list of batches containing 3 batches, when call findAll, then should return size equals 3")
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
    @DisplayName("Given an ID to non existing batch, when call findById, then throws an NotFoundException(\"Lote não encontrado\");")
    public void throwsToNonExistingBatch() {
        Mockito.when(batchRepository.findById(Mockito.any())).thenReturn(Optional.empty());

        NotFoundException thrown = Assertions.assertThrows(
                NotFoundException.class,
                () ->  batchService.findById(1L)
        );

        Assertions.assertEquals("Lote não encontrado", thrown.getMessage());
    }

    @Test
    @DisplayName("Given an existing Batch, when call updateBatch, then should update all properties")
    public void updateExistingBatch() throws NotFoundException {
        Batch batch = new Batch();
        Mockito.when(batchRepository.findById(1L)).thenReturn(Optional.of(batch));
        
        batch.setSeller_id(2L);
        batch.setPrice(BigDecimal.valueOf(33.0));
        batch.setOrder_number(2);
        batch.setBatch_number(2);
        batch.setQuantity(250);
        batch.setManufacturing_datetime(LocalDate.parse("2022-01-01"));
        batch.setDue_date(LocalDate.parse("2022-05-02"));

        batchService.updateBatch(1L, batch);

        Mockito.verify(batchRepository, Mockito.times(1)).save(batch);
    }

    @Test
    @DisplayName("Given a non existing batch, when call updateBatch, then throws an NotFoundException(\"Lote não encontrado\");")
    public void throwsWhenUpdateNonExistingBatch() {
        Batch updatedBatch = new Batch();
        Mockito.when(batchRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException thrown = Assertions.assertThrows(
                NotFoundException.class,
                () ->  batchService.updateBatch(1L, updatedBatch)
        );

        Assertions.assertEquals("Lote não encontrado", thrown.getMessage());
    }

    @Test
    @DisplayName("Given a existing batch, when call deleteBatch, then should call batchRepository.delete once with this batch")
    public void deleteBatchById() throws NotFoundException {
        Batch batch = new Batch();
        Mockito.when(batchRepository.findById(1L)).thenReturn(Optional.of(batch));

        batchService.delete(1L);

        Mockito.verify(batchRepository, Mockito.times(1)).delete(batch);
    }

    @Test
    @DisplayName("Given a non existing batch, when call deleteBatch, then throws an NotFoundException(\"Lote não encontrado\");")
    public void throwsWhenDeleteNonExistingBatch() {
        Mockito.when(batchRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException thrown = Assertions.assertThrows(
                NotFoundException.class,
                () ->  batchService.delete(1L)
        );

        Assertions.assertEquals("Lote não encontrado", thrown.getMessage());
    }

}
