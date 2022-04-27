package br.com.mercadolivre.projetointegrador.test_utils;

import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
import br.com.mercadolivre.projetointegrador.marketplace.repository.BatchRepository;
import br.com.mercadolivre.projetointegrador.marketplace.repository.ProductRepository;
import br.com.mercadolivre.projetointegrador.warehouse.model.Location;
import br.com.mercadolivre.projetointegrador.warehouse.model.Manager;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import br.com.mercadolivre.projetointegrador.warehouse.repository.ManagerRepository;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;
import br.com.mercadolivre.projetointegrador.warehouse.repository.WarehouseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class IntegrationTestUtils {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BatchRepository batchRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Autowired
    private ManagerRepository managerRepository;

    public Map<String, Object> generateFullScenario(){

        Product fakeProduct = new Product();
        fakeProduct.setName("new product");
        fakeProduct.setCategory("FS");
        Product product = productRepository.save(fakeProduct);

        Manager manager = managerRepository.save(
                new Manager()
        );


        Warehouse warehouse = warehouseRepository.save(
                new Warehouse(
                        "warehouse 01",
                        new Location(
                                "Brazil",
                                "SP",
                                "Osasco",
                                "Bomfim",
                                "Av. das Nações Unidas",
                                3003,
                                06233200
                        )

                )
        );



        Section section = sectionRepository.save(
                new Section(
                        null,
                        warehouse,
                        manager,
                        BigDecimal.valueOf(33.33),
                        BigDecimal.ZERO,
                        1000,
                        null
                )
        );


        Batch batch = batchRepository.save(
                new Batch(
                        null,
                        fakeProduct,
                        section,
                        1L,
                        BigDecimal.valueOf(129.99),
                        123,
                        1,
                        50,
                        null,
                        null,
                        null
                )
        );

        return Map.of(
                "product", product,
                "manager", manager,
                "warehouse", warehouse,
                "section", section,
                "batch", batch
        );
    }
}
