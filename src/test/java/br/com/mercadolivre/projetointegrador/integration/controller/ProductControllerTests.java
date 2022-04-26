package br.com.mercadolivre.projetointegrador.integration.controller;


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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(profiles = "test")
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

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

    ObjectMapper objectMapper = new ObjectMapper();

    Product fakeProduct;

    @BeforeEach
    public void beforeEach() {
        fakeProduct = new Product();
        fakeProduct.setName("new product");
        fakeProduct.setCategory("FS");
    }

    @Test
    @DisplayName("ProductController - POST - /api/v1/fresh-products")
    public void testCreateProduct() throws Exception {
        Product newProduct = fakeProduct;
        newProduct.setName(fakeProduct.getName().concat("new product"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/fresh-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newProduct))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
    }

    @Test
    @DisplayName("ProductController - GET - /api/v1/fresh-products/{id}")
    public void testFindProductById() throws Exception {
        productRepository.save(fakeProduct);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/{id}", 1L))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("new product"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("FS"));
    }

    @Test
    @DisplayName("ProductController - PUT - /api/v1/fresh-products/{id}")
    public void testUpdateProduct() throws Exception {
        productRepository.save(fakeProduct);

        Product updatedProduct = new Product();
        updatedProduct.setName("updated product");
        updatedProduct.setCategory("RF");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/fresh-products/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedProduct))
        )
        .andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn();

        Optional<Product> productDeleted = productRepository.findById(1L);
        Assertions.assertFalse(productDeleted.isEmpty());

        Assertions.assertEquals("updated product", productDeleted.get().getName());
        Assertions.assertEquals("RF", productDeleted.get().getCategory());
    }

    @Test
    @DisplayName("ProductController - GET - /api/v1/fresh-products")
    public void testFindAll() throws Exception {
        List<Product> response = new ArrayList<>();
        response.add(fakeProduct);

        productRepository.save(fakeProduct);
        mockMvc.perform(MockMvcRequestBuilders.
                        get("/api/v1/fresh-products"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(response))).andReturn();
    }

    @Test
    @DisplayName("ProductController - DELETE - /api/v1/fresh-products/{id}")
    public void testDeleteProduct() throws Exception {
        productRepository.save(fakeProduct);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/fresh-products/{id}", 1L))
            .andExpect(MockMvcResultMatchers.status().isNoContent());

        Optional<Product> productDeleted = productRepository.findById(1L);
        Assertions.assertTrue(productDeleted.isEmpty());
    }

    @Test
    @DisplayName("ProductController - GET - /api/v1/fresh-products/list")
    public void testListProducts() throws Exception {
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


        batchRepository.save(
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

        mockMvc.perform(MockMvcRequestBuilders.
                get("/api/v1/fresh-products/list?querytype=" + product.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.batchStock").isNotEmpty()).andReturn();
    }

}
