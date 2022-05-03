package br.com.mercadolivre.projetointegrador.integration.controller;

import br.com.mercadolivre.projetointegrador.warehouse.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.model.Product;
import br.com.mercadolivre.projetointegrador.warehouse.repository.BatchRepository;
import br.com.mercadolivre.projetointegrador.warehouse.repository.ProductRepository;
import br.com.mercadolivre.projetointegrador.test_utils.IntegrationTestUtils;
import br.com.mercadolivre.projetointegrador.test_utils.WithMockManagerUser;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.CreateBatchPayloadDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.InboundOrderDTO;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@WithMockManagerUser
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class InboundOrderControllerTests {
  private final String INBOUND_URL = "/api/v1/warehouse/inboundorder";
  @Autowired private MockMvc mockMvc;
  @Autowired private ProductRepository productRepository;
  @Autowired private BatchRepository batchRepository;
  @Autowired private IntegrationTestUtils integrationTestUtils;

  @Test
  public void TestIfInboundOrderIsCreated() throws Exception {
    integrationTestUtils.createRoles();
    integrationTestUtils.createUser();
    Section mockSection = integrationTestUtils.createSection();

    Product productMock = integrationTestUtils.createProduct();

    CreateBatchPayloadDTO batchMock =
        CreateBatchPayloadDTO.builder()
            .seller_id(1L)
            .quantity(2)
            .product_id(productMock.getId())
            .build();

    InboundOrderDTO objPayload =
        InboundOrderDTO.builder()
            .orderNumber(1)
            .batches(List.of(batchMock))
            .sectionCode(mockSection.getId())
            .warehouseCode(mockSection.getWarehouse().getId())
            .build();

    String payload = new ObjectMapper().writeValueAsString(objPayload);

    // ACT AND ASSERT
    MvcResult postResult =
        mockMvc
            .perform(
                MockMvcRequestBuilders.post(INBOUND_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(payload))
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andReturn();

    String link =
        JsonPath.read(postResult.getResponse().getContentAsString(), "$.[0]links[0][\"self\"]");

    mockMvc
        .perform(MockMvcRequestBuilders.get(link))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andReturn();
  }

  @Test
  public void TestIfDuplicatedInboundOrderIsCreated() throws Exception {
    Section mockSection = integrationTestUtils.createSection();

    Batch mockedBatch = integrationTestUtils.createBatch();

    CreateBatchPayloadDTO batchMock =
        CreateBatchPayloadDTO.builder()
            .batch_number(mockedBatch.getBatchNumber())
            .product_id(mockedBatch.getProduct().getId())
            .seller_id(mockedBatch.getSeller().getId())
            .quantity(2)
            .build();

    InboundOrderDTO objPayload =
        InboundOrderDTO.builder()
            .orderNumber(11)
            .batches(List.of(batchMock))
            .sectionCode(mockedBatch.getSection().getId())
            .warehouseCode(mockSection.getWarehouse().getId())
            .build();

    String payload = new ObjectMapper().writeValueAsString(objPayload);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(INBOUND_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
        .andExpect(MockMvcResultMatchers.status().isConflict())
        .andReturn();
  }

  @Test
  public void TestIfInboundOrderIsUpdated() throws Exception {
    // SETUP
    Section mockSection = integrationTestUtils.createSection();

    Batch mockedBatch = integrationTestUtils.createBatch(mockSection);

    CreateBatchPayloadDTO batchMock =
        CreateBatchPayloadDTO.builder()
            .quantity(2)
            .price(BigDecimal.valueOf(112.99))
            .batch_number(mockedBatch.getBatchNumber())
            .product_id(1L)
            .seller_id(mockedBatch.getSeller().getId())
            .build();

    InboundOrderDTO objPayload =
        InboundOrderDTO.builder()
            .orderNumber(1)
            .batches(List.of(batchMock))
            .sectionCode(mockedBatch.getSection().getId())
            .warehouseCode(mockSection.getWarehouse().getId())
            .build();

    String payload = new ObjectMapper().writeValueAsString(objPayload);

    mockMvc
        .perform(
            MockMvcRequestBuilders.put(INBOUND_URL)
                .header("Authorization", "sometoken")
                .contentType(MediaType.APPLICATION_JSON)
                .content(payload))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andReturn();

    Batch result = batchRepository.findById(mockedBatch.getId()).orElse(new Batch());

    Assertions.assertEquals(batchMock.getPrice(), result.getPrice());
  }
}
