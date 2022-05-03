package br.com.mercadolivre.projetointegrador.integration.controller;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.PurchaseOrderDTO;
import br.com.mercadolivre.projetointegrador.marketplace.enums.CartStatusCodeEnum;
import br.com.mercadolivre.projetointegrador.marketplace.model.Cart;
import br.com.mercadolivre.projetointegrador.test_utils.IntegrationTestUtils;
import br.com.mercadolivre.projetointegrador.test_utils.WithMockCustomUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles(profiles = "test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration
@WithMockCustomUser
public class CartControllerTests {
  private final String PURCHASEORDER_URL = "/api/v1/fresh-products/orders";
  @Autowired private MockMvc mockMvc;
  @Autowired private IntegrationTestUtils integrationTestUtils;
  ObjectMapper objectMapper =
      new ObjectMapper()
          .registerModule(new JavaTimeModule())
          .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

  @Test
  @DisplayName("CartController - POST - api/v1/fresh-products/orders")
  public void testCreateOrUpdatePurchaseOrder() throws Exception {

    PurchaseOrderDTO purchaseOrder = integrationTestUtils.createPurchaseOrder();

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(PURCHASEORDER_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(purchaseOrder)))
        .andExpect(MockMvcResultMatchers.status().isCreated())
        .andExpect(MockMvcResultMatchers.content().string("35.0"));
  }

  @Test
  @DisplayName("CartController - GET - api/v1/fresh-products/orders/{buyerId}")
  public void testShowOrder() throws Exception {

    Cart cart = integrationTestUtils.createCart();

    mockMvc
        .perform(MockMvcRequestBuilders.get(PURCHASEORDER_URL + "/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.statusCode").value(cart.getStatusCode().toString()))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.totalPrice").value(cart.getTotalPrice().toString()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(cart.getDate().toString()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.products", hasSize(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].productId").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].quantity").value(5))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.products[0].unitPrice")
                .value(cart.getProducts().get(0).getUnitPrice().toString()));
  }

  @Test
  @DisplayName("CartController - GET - api/v1/fresh-products/orders")
  public void testShowOrderAuthenticated() throws Exception {

    Cart cart = integrationTestUtils.createCart();

    mockMvc
        .perform(MockMvcRequestBuilders.get(PURCHASEORDER_URL))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.statusCode").value(cart.getStatusCode().toString()))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.totalPrice").value(cart.getTotalPrice().toString()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.date").value(cart.getDate().toString()))
        .andExpect(MockMvcResultMatchers.jsonPath("$.products", hasSize(1)))
        .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].productId").value(1))
        .andExpect(MockMvcResultMatchers.jsonPath("$.products[0].quantity").value(5))
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.products[0].unitPrice")
                .value(cart.getProducts().get(0).getUnitPrice().toString()));
  }

  @Test
  @DisplayName("CartController - PUT - api/v1/fresh-products/orders/{buyerId}?status=FINALIZADO")
  public void testUpdateOrderStatus() throws Exception {

    integrationTestUtils.createCart();

    mockMvc
        .perform(MockMvcRequestBuilders.put(PURCHASEORDER_URL + "?status=FINALIZADO"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.statusCode")
                .value(CartStatusCodeEnum.FINALIZADO.toString()));
  }

  @Test
  @DisplayName("CartController - PATCH - api/v1/fresh-products/orders/status")
  public void testSwitchStatus() throws Exception {

    integrationTestUtils.createCart();

    mockMvc
        .perform(MockMvcRequestBuilders.patch(PURCHASEORDER_URL + "/status"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andExpect(
            MockMvcResultMatchers.jsonPath("$.statusCode")
                .value(CartStatusCodeEnum.FINALIZADO.toString()));
  }
}
