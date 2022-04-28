package br.com.mercadolivre.projetointegrador.integration.controller;

import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
import br.com.mercadolivre.projetointegrador.marketplace.repository.BatchRepository;
import br.com.mercadolivre.projetointegrador.marketplace.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(profiles = "test")
public class BatchControllerTests {

    @Autowired private MockMvc mockMvc;

    @Autowired private BatchRepository batchRepository;

    @Autowired private ProductRepository productRepository;

    @Test
    @DisplayName("BatchController - GET - /api/v1/batches/{id}")
    public void testFindBatchById() throws Exception {

        Product product = new Product();
        productRepository.save(product);

        Batch batch = new Batch();
        batch.setProduct(product);
        batch.setSection_id(1L);
        batch.setSeller_id(2L);
        batch.setPrice(BigDecimal.valueOf(33.0));
        batch.setOrder_number(2);
        batch.setBatchNumber(2);
        batch.setQuantity(250);
        batch.setManufacturing_datetime(LocalDate.parse("2022-01-01"));
        batch.setDue_date(LocalDate.parse("2022-05-02"));

        Batch created = batchRepository.save(batch);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/batches/{id}", created.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.section_id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.seller_id").value(2L))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.price").value(BigDecimal.valueOf(33.0)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.order_number").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.batchNumber").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(250))
                .andExpect(
                        MockMvcResultMatchers.jsonPath("$.manufacturing_datetime")
                                .value("2022-01-01"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.due_date").value("2022-05-02"));
    }
}
