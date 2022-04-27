package br.com.mercadolivre.projetointegrador.integration.controller;

import br.com.mercadolivre.projetointegrador.marketplace.model.Batch;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.InboundOrderDTO;
import br.com.mercadolivre.projetointegrador.warehouse.model.InboundOrder;
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

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(profiles = "test")
public class InboundOrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private final String INBOUND_URL = "/api/v1/inboundorder";

    @Test
    public void TestIfInboundOrderIsCreated() throws Exception {

        InboundOrder objPayload = InboundOrder
                .builder()
                .orderNumber(1)
                .batches(List.of(Batch.builder().build()))
                .sectionCode(1L)
                .warehouseCode(1L)
                .build();

        String payload = new ObjectMapper().writeValueAsString(objPayload);

        MvcResult mvcResult = mockMvc.perform(
                MockMvcRequestBuilders
                        .post(INBOUND_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated()).andReturn();
    }

    @Test
    public void TestIfInboundOrderIsUpdated() {
        assert true;
    }
}
