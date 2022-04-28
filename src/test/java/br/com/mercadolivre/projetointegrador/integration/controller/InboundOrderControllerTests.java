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
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(profiles = "test")
public class InboundOrderControllerTests {

    private final String INBOUND_URL = "/api/v1/inboundorder";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private BatchRepository batchRepository;
    @Autowired
    private IntegrationTestUtils integrationTestUtils;

    @Test
    public void TestIfInboundOrderIsCreated() throws Exception {

        Section mockSection = integrationTestUtils.createSection();

        Product productMock = new Product(1L, "teste", CategoryEnum.FS, null);
        productRepository.save(productMock);

        CreateBatchPayloadDTO batchMock = CreateBatchPayloadDTO
                .builder()
                .quantity(2)
                .product_id(1L)
                .build();

        InboundOrderDTO objPayload = InboundOrderDTO
                .builder()
                .orderNumber(1)
                .batches(List.of(batchMock))
                .sectionCode(mockSection.getId())
                .warehouseCode(mockSection.getWarehouse().getId())
                .build();

        String payload = new ObjectMapper().writeValueAsString(objPayload);

        // ACT AND ASSERT
        MvcResult postResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(INBOUND_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        String link = JsonPath.read(postResult.getResponse().getContentAsString(), "$.[0]links[0][\"self\"]");

        mockMvc.perform(MockMvcRequestBuilders.get(link)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    public void TestIfDuplicatedInboundOrderIsCreated() throws Exception {
        Section mockSection = integrationTestUtils.createSection();

        Product productMock = new Product(1L, "teste", CategoryEnum.FS, null);
        productRepository.save(productMock);

    Batch mockedBatch = WarehouseTestUtils.getBatch().get(0);
        mockedBatch.setSection_id(mockSection.getId());
        mockedBatch.setPrice(BigDecimal.valueOf(11.99));

        Batch saved = batchRepository.save(mockedBatch);

        CreateBatchPayloadDTO batchMock = CreateBatchPayloadDTO
                .builder()
                .batch_number(saved.getBatchNumber())
                .product_id(saved.getProduct().getId())
                .seller_id(saved.getSeller_id())
                .quantity(2)
                .build();


        InboundOrderDTO objPayload = InboundOrderDTO
                .builder()
                .orderNumber(1)
                .batches(List.of(batchMock))
                .sectionCode(saved.getSection_id())
                .warehouseCode(mockSection.getWarehouse().getId())
                .build();

        String payload = new ObjectMapper().writeValueAsString(objPayload);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post(INBOUND_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isConflict()).andReturn();


    }

    @Test
    public void TestIfInboundOrderIsUpdated() throws Exception {
        // SETUP
        Section mockSection = integrationTestUtils.createSection();

        Product productMock = new Product(1L, "teste", CategoryEnum.FS, null);
        productRepository.save(productMock);

    Batch mockedBatch = WarehouseTestUtils.getBatch().get(0);
        mockedBatch.setSection_id(mockSection.getId());
        mockedBatch.setPrice(BigDecimal.valueOf(11.99));

        Batch saved = batchRepository.save(mockedBatch);

        CreateBatchPayloadDTO batchMock = CreateBatchPayloadDTO
                .builder()
                .quantity(2)
                .price(BigDecimal.valueOf(112.99))
                .batch_number(saved.getBatchNumber())
                .product_id(1L)
                .build();


        InboundOrderDTO objPayload = InboundOrderDTO
                .builder()
                .orderNumber(1)
                .batches(List.of(batchMock))
                .sectionCode(saved.getSection_id())
                .warehouseCode(mockSection.getWarehouse().getId())
                .build();

        String payload = new ObjectMapper().writeValueAsString(objPayload);

        mockMvc.perform(
                MockMvcRequestBuilders
                        .put(INBOUND_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();


        Batch result = batchRepository.findById(saved.getId()).orElse(new Batch());

        Assertions.assertEquals(batchMock.getPrice(), result.getPrice());

    }
}
