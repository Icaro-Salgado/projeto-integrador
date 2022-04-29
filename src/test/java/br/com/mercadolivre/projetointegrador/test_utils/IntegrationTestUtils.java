package br.com.mercadolivre.projetointegrador.test_utils;

import br.com.mercadolivre.projetointegrador.warehouse.enums.CategoryEnum;
import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.model.Product;
import br.com.mercadolivre.projetointegrador.warehouse.repository.BatchRepository;
import br.com.mercadolivre.projetointegrador.warehouse.repository.ProductRepository;
import br.com.mercadolivre.projetointegrador.warehouse.model.Location;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;
import br.com.mercadolivre.projetointegrador.warehouse.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class IntegrationTestUtils {

  @Autowired private WarehouseRepository warehouseRepository;

  @Autowired private SectionRepository sectionRepository;

  @Autowired private ProductRepository productRepository;

  @Autowired private BatchRepository batchRepository;

  public Warehouse createWarehouse() {
    Warehouse warehouse =
        Warehouse.builder()
            .name("Mocked warehouse")
            .location(
                new Location(
                    "Brazil",
                    "SP",
                    "Osasco",
                    "Bomfim",
                    "Av. das Nações Unidas",
                    ThreadLocalRandom.current().nextInt(),
                    6233200))
            .build();

    return warehouseRepository.save(warehouse);
  }

  public Section createSection() {
    Warehouse warehouse = createWarehouse();

    return sectionRepository.save(
        new Section(
            null,
            warehouse,
            1L,
            BigDecimal.valueOf(33.33),
            BigDecimal.ZERO,
            1000,
            CategoryEnum.FS,
            null));
  }

  public Product createProduct() {
    Product productMock = new Product(1L, "teste", CategoryEnum.FS, null);
    return productRepository.save(productMock);
  }

  public Batch createBatch() {
    Batch batch =
        Batch.builder()
            .product(createProduct())
            .section_id(createSection().getId())
            .seller_id(1L)
            .price(BigDecimal.TEN)
            .order_number(123)
            .batchNumber(9595)
            .quantity(10)
            .build();

    return batchRepository.save(batch);
  }

  public Batch createBatch(Section section) {
    Batch batch =
        Batch.builder()
            .product(createProduct())
            .section_id(section.getId())
            .seller_id(1L)
            .price(BigDecimal.TEN)
            .order_number(123)
            .batchNumber(9595)
            .quantity(10)
            .build();

    return batchRepository.save(batch);
  }

  public void resetDatabase() {}
}
