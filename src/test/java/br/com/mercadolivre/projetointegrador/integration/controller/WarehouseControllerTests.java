package br.com.mercadolivre.projetointegrador.integration.controller;

import br.com.mercadolivre.projetointegrador.warehouse.dto.request.CreateWarehousePayloadDTO;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.RequestLocationDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(profiles = "test")
@WithMockUser
public class WarehouseControllerTests {

  private final String SECTION_URL = "/api/v1/warehouse";
  ObjectMapper objectMapper = new ObjectMapper();
  @Autowired private MockMvc mockMvc;

  @Test
  public void shouldCreateNewWarehouse() throws Exception {
    CreateWarehousePayloadDTO payloadDTO =
        new CreateWarehousePayloadDTO(
            "Warehouse test",
            new RequestLocationDTO(
                "Brazil", "SP", "Osasco", "Bomfim", "Av. das Nações Unidas", 3003, 6233200));

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
}
