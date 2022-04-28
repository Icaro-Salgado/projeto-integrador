package br.com.mercadolivre.projetointegrador.integration.controller;


import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
import br.com.mercadolivre.projetointegrador.marketplace.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

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

    ObjectMapper objectMapper = new ObjectMapper();

    Product fakeProduct;

    @BeforeEach
    public void beforeEach() {
        fakeProduct = new Product();
        fakeProduct.setCategory("RF");
    }

    @Test
    @DisplayName("ProductController - POST - /api/v1/fresh-products")
    public void testCreateProduct() throws Exception {

        fakeProduct.setName("Post Product");

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/fresh-products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(fakeProduct))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();
    }

    @Test
    @DisplayName("ProductController - GET - /api/v1/fresh-products/{id}")
    public void testFindProductById() throws Exception {

        fakeProduct.setName("Get Id Product");
        productRepository.save(fakeProduct);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/fresh-products/{id}", fakeProduct.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Get Id Product"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.category").value("RF"));
    }

    @Test
    @DisplayName("ProductController - PUT - /api/v1/fresh-products/{id}")
    public void testUpdateProduct() throws Exception {
        fakeProduct.setName("PUT product");
        productRepository.save(fakeProduct);

        Product updatedProduct = new Product();
        updatedProduct.setName("updated product");
        updatedProduct.setCategory("FS");

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/fresh-products/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedProduct))
        )
        .andExpect(MockMvcResultMatchers.status().isNoContent()).andReturn();

        Optional<Product> productDeleted = productRepository.findById(1L);
        Assertions.assertFalse(productDeleted.isEmpty());

        Assertions.assertEquals("updated product", productDeleted.get().getName());
        Assertions.assertEquals("FS", productDeleted.get().getCategory());
    }

    @Test
    @DisplayName("ProductController - GET - /api/v1/fresh-products")
    public void testFindAll() throws Exception {
        List<Product> response = new ArrayList<>();
        fakeProduct.setName("GET ALL product");
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
        fakeProduct.setName("DELETE product");
        productRepository.save(fakeProduct);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/fresh-products/{id}", 1L))
            .andExpect(MockMvcResultMatchers.status().isNoContent());

        Optional<Product> productDeleted = productRepository.findById(1L);
        Assertions.assertTrue(productDeleted.isEmpty());
    }
}
