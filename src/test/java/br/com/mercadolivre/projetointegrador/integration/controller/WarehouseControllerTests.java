package br.com.mercadolivre.projetointegrador.integration.controller;

import br.com.mercadolivre.projetointegrador.test_utils.IntegrationTestUtils;
import br.com.mercadolivre.projetointegrador.test_utils.WithMockManagerUser;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.CreateWarehousePayloadDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.RequestLocationDTO;
import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.model.Product;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(profiles = "test")
@WithMockManagerUser
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class WarehouseControllerTests {

  private final String SECTION_URL = "/api/v1/warehouse";
  ObjectMapper objectMapper = new ObjectMapper();
  @Autowired private MockMvc mockMvc;

  @Autowired private IntegrationTestUtils integrationTestUtils;

  @Test
  public void shouldCreateNewWarehouse() throws Exception {
    CreateWarehousePayloadDTO payloadDTO =
        new CreateWarehousePayloadDTO(
            "Warehouse test",
            new RequestLocationDTO(
                "Brazil", "SP", "Osasco", "Bomfim", "Av. das Nações Unidas", 9999, 6233200));

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(SECTION_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payloadDTO)))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  public void shouldReturn4XXWhenReceiveInvalidValue() throws Exception {
    CreateWarehousePayloadDTO payloadDTO =
        new CreateWarehousePayloadDTO(
            "Warehouse test",
            RequestLocationDTO.builder()
                .country("Brazil")
                .state("SP")
                .city("Osasco")
                .neighborhood("Bomfim")
                .street("Av. das Nações Unidas")
                .number(3003)
                .build());

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(SECTION_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payloadDTO)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
  }

  @Test
  public void shouldListSectionBatches() throws Exception {
    Batch batch = integrationTestUtils.createBatch();

    mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    SECTION_URL.concat(
                        "/fresh-products/list?product=" + batch.getProduct().getId()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$.batchStock").isNotEmpty());
  }

  @Test
  public void shouldListSectionBatchesOrderedByBatchNumber() throws Exception {
    List<Batch> batch = integrationTestUtils.createMultipleBatchesOnSameWarehouse();

    List<Integer> batchNumbers =
        batch.stream().map(Batch::getBatchNumber).collect(Collectors.toList());
    Collections.sort(batchNumbers);

    MvcResult mvcResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get(
                        SECTION_URL.concat(
                                "/fresh-products/list?product=" + batch.get(0).getProduct().getId())
                            + "&sort=L")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.batchStock").isNotEmpty())
            .andReturn();

    String contentAsString = mvcResult.getResponse().getContentAsString();

    for (Integer i = 0; i < batch.size(); i++) {
      String value =
          JsonPath.read(contentAsString, "batchStock[".concat(i.toString()).concat("]batchNumber"))
              .toString();

      Assertions.assertEquals(value, batchNumbers.get(i).toString());
    }
  }

  @Test
  public void shouldListSectionBatchesOrderedByQuantity() throws Exception {
    List<Batch> batch = integrationTestUtils.createMultipleBatchesOnSameWarehouse();

    List<Integer> batchNumbers =
        batch.stream().map(Batch::getQuantity).collect(Collectors.toList());
    Collections.sort(batchNumbers);

    MvcResult mvcResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get(
                        SECTION_URL.concat(
                                "/fresh-products/list?product=" + batch.get(0).getProduct().getId())
                            + "&sort=C")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.batchStock").isNotEmpty())
            .andReturn();

    String contentAsString = mvcResult.getResponse().getContentAsString();

    System.out.println(contentAsString);

    for (Integer i = 0; i < batch.size(); i++) {
      String value =
          JsonPath.read(contentAsString, "batchStock[".concat(i.toString()).concat("]quantity"))
              .toString();

      Assertions.assertEquals(value, batchNumbers.get(i).toString());
    }
  }

  @Test
  public void shouldListSectionBatchesOrderedByDueDate() throws Exception {
    List<Batch> batch = integrationTestUtils.createMultipleBatchesOnSameWarehouse();

    List<LocalDate> batchNumbers =
        batch.stream().map(Batch::getDueDate).collect(Collectors.toList());
    Collections.sort(batchNumbers);

    MvcResult mvcResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.get(
                        SECTION_URL.concat(
                                "/fresh-products/list?product=" + batch.get(0).getProduct().getId())
                            + "&sort=F")
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("$.batchStock").isNotEmpty())
            .andReturn();

    String contentAsString = mvcResult.getResponse().getContentAsString();

    for (int i = 0; i < batch.size(); i++) {
      String value =
          JsonPath.read(
                  contentAsString, "batchStock[".concat(Integer.toString(i)).concat("]dueDate"))
              .toString();

      Assertions.assertEquals(value, batchNumbers.get(i).toString());
    }
  }

  @Test
  public void shouldReturn404WhenNotFoundAnyResult() throws Exception {
    Product product = integrationTestUtils.createProduct();

    mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    SECTION_URL.concat("/fresh-products/list?product=" + product.getId()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }

  @Test
  public void shouldReturnInvalidParameterErrorWhenNotReceiveProductId() throws Exception {
    mockMvc
        .perform(
            MockMvcRequestBuilders.get(SECTION_URL.concat("/fresh-products/list"))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.error").isNotEmpty());
  }

  @Test
  public void shouldListDueDateBatchesInSection() throws Exception {
    Batch batch = integrationTestUtils.okBatch();

    mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    SECTION_URL.concat(
                        "/fresh-products/duedate?numb_days=11&section_id="
                            + batch.getSection().getId()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
  }

  @Test
  public void shouldFailListDueDateBatchesInSection() throws Exception {
    Batch batch = integrationTestUtils.okBatch();

    mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    SECTION_URL.concat(
                        "/fresh-products/duedate?numb_days=10&section_id="
                            + batch.getSection().getId()))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
  }

  @Test
  public void shouldListDueDateBatch() throws Exception {
    Batch batch = integrationTestUtils.dueDateFiveDays();

    mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    SECTION_URL.concat(
                        "/fresh-products/duedate-batches?numb_days=10&category="
                            + batch.getProduct().getCategory()
                            + "&order=ASC"))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$").isNotEmpty());
  }

  @Test
  public void shouldFailListDueDateBatch() throws Exception {
    Batch batch = integrationTestUtils.dueDateFifteenDays();

    mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    SECTION_URL.concat(
                        "/fresh-products/duedate-batches?numb_days=10&category="
                            + batch.getProduct().getCategory()
                            + "&order=ASC"))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
  }

  @Test
  public void shouldFailListDueDateBatchByCategory() throws Exception {
    Batch batch = integrationTestUtils.dueDateFiveDays();

    mockMvc
        .perform(
            MockMvcRequestBuilders.get(
                    SECTION_URL.concat(
                        "/fresh-products/duedate-batches?numb_days=10&category=FF&order=ASC"))
                .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(MockMvcResultMatchers.jsonPath("$").isEmpty());
  }
}
