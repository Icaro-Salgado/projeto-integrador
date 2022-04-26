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


}
