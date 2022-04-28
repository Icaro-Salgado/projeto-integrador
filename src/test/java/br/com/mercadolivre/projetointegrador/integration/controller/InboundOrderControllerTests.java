package br.com.mercadolivre.projetointegrador.integration.controller;

import br.com.mercadolivre.projetointegrador.marketplace.enums.CategoryEnum;
import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
import br.com.mercadolivre.projetointegrador.marketplace.repository.ProductRepository;
import br.com.mercadolivre.projetointegrador.test_utils.IntegrationTestUtils;
import br.com.mercadolivre.projetointegrador.test_utils.SectionServiceTestUtils;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.CreateBatchPayloadDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.InboundOrderDTO;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
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

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(profiles = "test")
public class InboundOrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private IntegrationTestUtils integrationTestUtils;

    private final String INBOUND_URL = "/api/v1/inboundorder";

    @Test
    public void TestIfInboundOrderIsCreated() throws Exception {

        Section mockSection = integrationTestUtils.createSection();

        Product productMock = new Product(1L, "teste", CategoryEnum.FS, null);
        productRepository.save(productMock);

        CreateBatchPayloadDTO batchMock = CreateBatchPayloadDTO
                .builder()
                .quantity(2)
                .product(productMock)
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
    public void TestIfInboundOrderIsUpdated() throws Exception {
        // SETUP
        Section mockSection = integrationTestUtils.createSection();

        Product productMock = new Product(1L, "teste", CategoryEnum.FS, null);
        productRepository.save(productMock);

        CreateBatchPayloadDTO batchMock = CreateBatchPayloadDTO
                .builder()
                .quantity(2)
                .product(productMock)
                .build();

        InboundOrderDTO objPayload = InboundOrderDTO
                .builder()
                .orderNumber(1)
                .batches(List.of(batchMock))
                .sectionCode(mockSection.getId())
                .warehouseCode(1L)
                .build();

        String payload = new ObjectMapper().writeValueAsString(objPayload);

        // ACT AND ASSERT
        MvcResult postResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .put(INBOUND_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        String link = JsonPath.read(postResult.getResponse().getContentAsString(), "$.[0]links[0][\"self\"]");

        mockMvc.perform(MockMvcRequestBuilders.get(link)).andExpect(MockMvcResultMatchers.status().isOk()).andReturn();    }
}
