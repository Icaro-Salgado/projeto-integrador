package br.com.mercadolivre.projetointegrador.integration.controller;

import br.com.mercadolivre.projetointegrador.marketplace.dto.CartProductDTO;
import br.com.mercadolivre.projetointegrador.marketplace.dto.PurchaseOrderDTO;
import br.com.mercadolivre.projetointegrador.marketplace.model.Product;
import br.com.mercadolivre.projetointegrador.marketplace.repository.ProductRepository;
import br.com.mercadolivre.projetointegrador.marketplace.repository.RedisRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
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
import java.util.List;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles(profiles = "test")
public class CartControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RedisRepository redisRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("CartController - POST - api/v1/fresh-products/orders")
    public void testOrderPrice() throws Exception {

        PurchaseOrderDTO purchaseOrder = new PurchaseOrderDTO();
        purchaseOrder.setId(1L);

        CartProductDTO product1 = new CartProductDTO();
        product1.setProductId(1L);
        product1.setQuantity(5);
        product1.setUnitPrice(BigDecimal.valueOf(7.00));

        List<CartProductDTO> products = List.of(product1);
        purchaseOrder.setProducts(products);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/fresh-products/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(purchaseOrder))
        )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.content().string("35.0"));
    }
}
