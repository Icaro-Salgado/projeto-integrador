package br.com.mercadolivre.projetointegrador.integration.controller;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.CreatePurchaseDTO;
import br.com.mercadolivre.projetointegrador.marketplace.model.Ad;
import br.com.mercadolivre.projetointegrador.marketplace.model.AdPurchase;
import br.com.mercadolivre.projetointegrador.marketplace.model.Purchase;
import br.com.mercadolivre.projetointegrador.marketplace.repository.AdPurchaseRepository;
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
import org.springframework.test.annotation.DirtiesContext;
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
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@WithMockCustomerUser
public class PurchaseControllerTests {

  private final String PURCHASE_URL = "/api/v1/customers/marketplace/purchases";
  @Autowired private MockMvc mockMvc;
  @Autowired private PurchaseRepository purchaseRepository;
  @Autowired private AdRepository adRepository;
  @Autowired private AdPurchaseRepository adPurchaseRepository;
  @Autowired private IntegrationTestUtils integrationTestUtils;
  ObjectMapper objectMapper = new ObjectMapper();

  @BeforeEach
  public void createPurchase() {
    Ad ad = integrationTestUtils.createAdDTO().DTOtoModel();
    adRepository.save(ad);

    Purchase purchase = new Purchase();
    purchase.setBuyerId(1L);
    purchaseRepository.save(purchase);

    AdPurchase adPurchase = new AdPurchase();
    adPurchase.setId(1L);
    adPurchase.setAd(ad);
    adPurchase.setQuantity(10);
    adPurchase.setPurchase(purchase);
    adPurchaseRepository.save(adPurchase);
  }

  @Test
  @DisplayName("PurchaseController - POST - /api/v1/marketplace/purchases")
  public void testCreatePurchase() throws Exception {

    integrationTestUtils.createCart();

    List<CreatePurchaseDTO> purchases = integrationTestUtils.createPurchases();

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(PURCHASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(purchases)))
        .andExpect(MockMvcResultMatchers.status().isCreated());
  }

  @Test
  @DisplayName("PurchaseController - GET - /api/v1/customers/marketplace/purchases/{id}")
  public void testListCustomerPurchases() throws Exception {

    mockMvc
        .perform(MockMvcRequestBuilders.get(PURCHASE_URL + "/1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$", hasSize(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].products[0].quantity").value(10))
        .andExpect(MockMvcResultMatchers.jsonPath("$[0].products[0].name").value("Fake Ad"));
  }

  @Test
  @DisplayName(
      "PurchaseController - PUT - /api/v1/customer/marketplace/purchases/{buyerId}/{purchaseId}")
  public void testUpdatePurchaseStatus() throws Exception {

    mockMvc
        .perform(MockMvcRequestBuilders.put(PURCHASE_URL + "/1/1"))
        .andExpect(MockMvcResultMatchers.jsonPath("$.statusCode").value("FINALIZADO"));
  }

  @Test
  @DisplayName("PurchaseController - NotFoundException")
  public void testPurchaseNotFound() throws Exception {

    List<CreatePurchaseDTO> purchases = integrationTestUtils.createPurchases();

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(PURCHASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(purchases)))
        .andExpect(MockMvcResultMatchers.status().isNotFound());
  }
}
