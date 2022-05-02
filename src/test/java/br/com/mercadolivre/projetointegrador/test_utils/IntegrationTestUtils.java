package br.com.mercadolivre.projetointegrador.test_utils;

import br.com.mercadolivre.projetointegrador.warehouse.enums.CategoryEnum;
import br.com.mercadolivre.projetointegrador.warehouse.model.*;
import br.com.mercadolivre.projetointegrador.warehouse.repository.BatchRepository;
import br.com.mercadolivre.projetointegrador.warehouse.repository.ProductRepository;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;
import br.com.mercadolivre.projetointegrador.warehouse.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class IntegrationTestUtils {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BatchRepository batchRepository;

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
                        .section(createSection())
                        .seller_id(1L)
                        .price(BigDecimal.TEN)
                        .order_number(123)
                        .batchNumber(9595)
                        .quantity(10)
                        .build();

        return batchRepository.save(batch);
    }


    public List<Batch> createMultipleBatchesOnSameWarehouse() {

        Random random = new Random();
        List<Batch> batchesToCreate = new ArrayList<>();

        Section section = createSection();

        for (int i = 0; i < 5; i++) {
            batchesToCreate.add(
                    Batch.builder()
                            .product(createProduct())
                            .section(section)
                            .seller_id(1L + i)
                            .price(BigDecimal.valueOf(10 * i))
                            .order_number(i)
                            .batchNumber(5 - i)
                            .quantity(random.nextInt(350))
                            .due_date(LocalDate.of(random.nextInt(2021) + 1977, random.nextInt(12) + 1, random.nextInt(27) + 1))
                            .build()
            );
        }

        return batchRepository.saveAll(batchesToCreate);
    }


    public Batch createBatch(Section section) {
        Batch batch =
                Batch.builder()
                        .product(createProduct())
                        .section(createSection())
                        .seller_id(1L)
                        .price(BigDecimal.TEN)
                        .order_number(123)
                        .batchNumber(9595)
                        .quantity(10)
                        .build();

        return batchRepository.save(batch);
    }

    public void resetDatabase() {
    }
}
