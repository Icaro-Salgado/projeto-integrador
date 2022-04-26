package br.com.mercadolivre.projetointegrador.integration.controller;

import br.com.mercadolivre.projetointegrador.warehouse.dto.request.InboundOrderDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
public class InboundOrderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    private final String INBOUND_URL = "/api/v1/inboundorder";

    @Test
    public void TestIfInboundOrderIsCreated() throws Exception {
        String payload = new ObjectMapper().writeValueAsString(InboundOrderDTO.builder().build());

        mockMvc.perform(
                MockMvcRequestBuilders
                        .post(INBOUND_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void TestIfInboundOrderIsUpdated() {
        assert true;
    }
}
