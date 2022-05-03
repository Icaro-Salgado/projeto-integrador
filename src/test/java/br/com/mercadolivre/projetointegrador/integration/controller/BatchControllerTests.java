package br.com.mercadolivre.projetointegrador.integration.controller;

import br.com.mercadolivre.projetointegrador.test_utils.IntegrationTestUtils;
import br.com.mercadolivre.projetointegrador.test_utils.WithMockManagerUser;
import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.model.Product;
import br.com.mercadolivre.projetointegrador.warehouse.repository.BatchRepository;
import br.com.mercadolivre.projetointegrador.warehouse.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
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
@WithMockManagerUser
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class BatchControllerTests {

  @Autowired private MockMvc mockMvc;

  @Autowired private BatchRepository batchRepository;

  @Autowired private ProductRepository productRepository;

  @Autowired private IntegrationTestUtils integrationTestUtils;

  private final String API_URL = "/api/v1/warehouse/batches";

  @Test
  @DisplayName("BatchController - GET - /api/v1/batches/{id}")
  public void testFindBatchById() throws Exception {

    Product product = new Product();
    productRepository.save(product);

    Batch batch = new Batch();
    batch.setProduct(product);
    batch.setSection(integrationTestUtils.createSection());
    batch.setSeller(integrationTestUtils.createUser());
    batch.setPrice(BigDecimal.valueOf(33.0));
    batch.setOrder_number(2);
    batch.setBatchNumber(2);
    batch.setQuantity(250);
    batch.setManufacturing_datetime(LocalDate.parse("2022-01-01"));
    batch.setDueDate(LocalDate.parse("2022-05-02"));

    Batch created = batchRepository.save(batch);

    mockMvc
        .perform(MockMvcRequestBuilders.get(API_URL + "/{id}", created.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.section_id").value(created.getSection().getId()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.seller").isNotEmpty())
        .andExpect(MockMvcResultMatchers.jsonPath("$.price").value(BigDecimal.valueOf(33.0)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.order_number").value(2))
        .andExpect(MockMvcResultMatchers.jsonPath("$.batchNumber").value(2))
        .andExpect(MockMvcResultMatchers.jsonPath("$.quantity").value(250))
        .andExpect(MockMvcResultMatchers.jsonPath("$.manufacturing_datetime").value("2022-01-01"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.dueDate").value("2022-05-02"));
  }
  
  @Test
  @DisplayName("BatchController - GET - /api/v1/batches/ad/{sellerId}")
  public void testIfReturnBatchesWithMoreThan3weeksOfDueDate() throws Exception {
    Batch batch = integrationTestUtils.createBatch();

    mockMvc
        .perform(MockMvcRequestBuilders.get(API_URL + "/ad/{sellerId}", batch.getSeller().getId()))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
  }
}
