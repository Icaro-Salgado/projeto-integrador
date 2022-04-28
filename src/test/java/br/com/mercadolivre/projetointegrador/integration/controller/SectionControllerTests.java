package br.com.mercadolivre.projetointegrador.integration.controller;

import br.com.mercadolivre.projetointegrador.marketplace.enums.CategoryEnum;
import br.com.mercadolivre.projetointegrador.test_utils.IntegrationTestUtils;
import br.com.mercadolivre.projetointegrador.warehouse.dto.request.CreateSectionPayloadDTO;
import br.com.mercadolivre.projetointegrador.warehouse.model.Section;
import br.com.mercadolivre.projetointegrador.warehouse.model.Warehouse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(profiles = "test")
public class SectionControllerTests {

  @Autowired private MockMvc mockMvc;

  @Autowired private IntegrationTestUtils integrationTestUtils;

  ObjectMapper objectMapper = new ObjectMapper();

  private final String SECTION_URL = "/api/v1/section";

  @Test
  public void shouldReturnObjectContainingSection() throws Exception {
    Section section = integrationTestUtils.createSection();

    mockMvc
        .perform(MockMvcRequestBuilders.get(SECTION_URL.concat("/{id}"), section.getId()))
        .andExpect(MockMvcResultMatchers.status().isOk());
  }

  @Test
  public void shouldCreateNewSection() throws Exception {
    Warehouse warehouse = integrationTestUtils.createWarehouse();

    CreateSectionPayloadDTO payloadDTO =
        new CreateSectionPayloadDTO(
            warehouse.getId(),
            1L,
            BigDecimal.valueOf(12.22),
            BigDecimal.valueOf(20.18),
            1000,
            CategoryEnum.FS);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(SECTION_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payloadDTO)))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  public void shouldReturn4XXWhenReceiveInvalidValue() throws Exception {
    Warehouse warehouse = integrationTestUtils.createWarehouse();

    CreateSectionPayloadDTO payloadDTO =
        new CreateSectionPayloadDTO(
            warehouse.getId(),
            1L,
            BigDecimal.valueOf(12.223),
            BigDecimal.valueOf(20.18),
            1000,
            CategoryEnum.FS);

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(SECTION_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(payloadDTO)))
        .andExpect(MockMvcResultMatchers.status().isBadRequest())
        .andExpect(MockMvcResultMatchers.jsonPath("$.minimumTemperature").isNotEmpty());
  }
}
