package br.com.mercadolivre.projetointegrador.integration.controller;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.CreatePurchaseDTO;
import br.com.mercadolivre.projetointegrador.marketplace.model.Ad;
import br.com.mercadolivre.projetointegrador.marketplace.model.Purchase;
import br.com.mercadolivre.projetointegrador.marketplace.repository.AdRepository;
import br.com.mercadolivre.projetointegrador.marketplace.repository.PurchaseRepository;
import br.com.mercadolivre.projetointegrador.test_utils.IntegrationTestUtils;
import br.com.mercadolivre.projetointegrador.test_utils.WithMockCustomerUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@WithMockCustomerUser
public class PurchaseControllerTests {

  private final String PURCHASE_URL = "/api/v1/customers/marketplace/purchases";
  @Autowired private MockMvc mockMvc;
  @Autowired private PurchaseRepository purchaseRepository;
  @Autowired private AdRepository adRepository;
  @Autowired private IntegrationTestUtils integrationTestUtils;
  ObjectMapper objectMapper = new ObjectMapper();
  Ad ad = new Ad();

  @Test
  @DisplayName("PurchaseController - POST - /api/v1/marketplace/purchases")
  public void testCreatePurchase() throws Exception {

    List<CreatePurchaseDTO> purchases = integrationTestUtils.createPurchases();

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(PURCHASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(purchases)))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  @DisplayName("PurchaseController - GET - /api/v1/customers/marketplace/purchases")
  public void testListCustomerPurchases() throws Exception {

    Purchase purchase = new Purchase();
    purchase.setBuyerId(1L);
    List<Purchase> purchases = List.of(purchase);
    purchaseRepository.saveAllAndFlush(purchases);

    mockMvc
        .perform(MockMvcRequestBuilders.get(PURCHASE_URL))
        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].buyerId").value(1));
  }
}
