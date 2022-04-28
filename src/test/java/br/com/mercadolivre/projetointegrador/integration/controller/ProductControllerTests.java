package br.com.mercadolivre.projetointegrador.integration.controller;


import br.com.mercadolivre.projetointegrador.marketplace.enums.CategoryEnum;
import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
import br.com.mercadolivre.projetointegrador.marketplace.repository.BatchRepository;
import br.com.mercadolivre.projetointegrador.marketplace.repository.ProductRepository;
import br.com.mercadolivre.projetointegrador.test_utils.IntegrationTestUtils;
import br.com.mercadolivre.projetointegrador.test_utils.WarehouseTestUtils;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.CreateBatchPayloadDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.InboundOrderDTO;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(profiles = "test")
@WithMockUser
public class ProductControllerTests {

    ObjectMapper objectMapper = new ObjectMapper();
    Product fakeProduct;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private BatchRepository batchRepository;
    @Autowired
    private IntegrationTestUtils integrationTestUtils;

    @BeforeEach
    public void beforeEach() {
        fakeProduct = new Product();
        fakeProduct.setName("new product");
        fakeProduct.setCategory(CategoryEnum.FS);
    }

    @Test
    @DisplayName("ProductController - POST - /api/v1/fresh-products")
    public void testCreateProduct() throws Exception {
        Product productToCreate = fakeProduct;
        productToCreate.setName(productToCreate.getName().concat("new product"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/fresh-products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productToCreate))
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
    }

    @Test
    @DisplayName("ProductController - POST - /api/v1/fresh-products")
    public void testCreateProductWithDuplicatedName() throws Exception {
        Product productToCreate = fakeProduct;
        productToCreate.setName(productToCreate.getName().concat("duplicated"));
        productRepository.save(productToCreate);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/fresh-products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productToCreate))
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").isNotEmpty())
                .andReturn();
    }

    @Test
    @DisplayName("ProductController - POST - /api/v1/fresh-products")
    public void testCreateProductWithInvalidCategory() throws Exception {
        Map<String, Object> productMap = Map.of(
                "name", "mocked product",
                "category", "invalid"
        );

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/fresh-products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productMap))
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.error").isNotEmpty())
                .andReturn();
    }

    @Test
    @DisplayName("ProductController - GET - /api/v1/fresh-products/{id}")
    public void testFindProductById() throws Exception {
        Product product = productRepository.save(fakeProduct);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/{id}", product.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("new product"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value(product.getCategory().toString()));
    }

    @Test
    @DisplayName("ProductController - PUT - /api/v1/fresh-products/{id}")
    public void testUpdateProduct() throws Exception {
        Product created = productRepository.save(fakeProduct);

        Product updatedProduct = new Product();
        updatedProduct.setName("updated product");
        updatedProduct.setCategory(CategoryEnum.RF);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/fresh-products/{id}", created.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedProduct))
        )
                .andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn();

        Optional<Product> productDeleted = productRepository.findById(created.getId());
        Assertions.assertFalse(productDeleted.isEmpty());

        Assertions.assertEquals("updated product", productDeleted.get().getName());
        Assertions.assertEquals(updatedProduct.getCategory(), productDeleted.get().getCategory());
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
                .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty()).andReturn();
    }

    @Test
    @DisplayName("ProductController - DELETE - /api/v1/fresh-products/{id}")
    public void testDeleteProduct() throws Exception {
        Product newProduct = productRepository.save(fakeProduct);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/fresh-products/{id}", newProduct.getId()))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        Optional<Product> productDeleted = productRepository.findById(newProduct.getId());
        Assertions.assertTrue(productDeleted.isEmpty());
    }

}
