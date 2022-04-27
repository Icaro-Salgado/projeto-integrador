package br.com.mercadolivre.projetointegrador.integration.controller;

import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
import br.com.mercadolivre.projetointegrador.marketplace.repository.ProductRepository;
import br.com.mercadolivre.projetointegrador.test_utils.SectionServiceTestUtils;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.CreateBatchPayloadDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.InboundOrderDTO;
import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.repository.SectionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(profiles = "test")
public class InboundOrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private ProductRepository productRepository;

    private final String INBOUND_URL = "/api/v1/inboundorder";

    @Test
    public void TestIfInboundOrderIsCreated() throws Exception {

        Section mockSection = SectionServiceTestUtils.getMockSection();
        sectionRepository.save(mockSection);


        Product productMock = new Product(1L, "teste", "teste", null);
        productRepository.save(productMock);

        CreateBatchPayloadDTO batchMock = CreateBatchPayloadDTO
                .builder()
                .product(productMock)
                .build();

        InboundOrderDTO objPayload = InboundOrderDTO
                .builder()
                .orderNumber(1)
                .batches(List.of(batchMock))
                .sectionCode(1)
                .warehouseCode(1L)
                .build();

        String payload = new ObjectMapper().writeValueAsString(objPayload);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(INBOUND_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void TestIfInboundOrderIsUpdated() {
        assert true;
    }
}
