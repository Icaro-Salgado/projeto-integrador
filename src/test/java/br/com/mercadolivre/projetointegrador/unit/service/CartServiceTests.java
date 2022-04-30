package br.com.mercadolivre.projetointegrador.unit.service;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.CartProductDTO;
import br.com.mercadolivre.projetointegrador.marketplace.enums.CartStatusCodeEnum;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.InvalidStatusCodeException;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Cart;
import br.com.mercadolivre.projetointegrador.marketplace.repository.RedisRepository;
import br.com.mercadolivre.projetointegrador.marketplace.services.CartService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CartServiceTests {

  RedisRepository redisRepository = Mockito.mock(RedisRepository.class);

  ObjectMapper objectMapper = new ObjectMapper();

  CartService cartService = new CartService(redisRepository, objectMapper);

  private Cart cart;

  @BeforeEach
  public void createMockCart() {
    cart = new Cart();
    cart.setStatusCode(CartStatusCodeEnum.ABERTO);

    CartProductDTO cartProduct = new CartProductDTO();
    cartProduct.setProductId(1L);
    cartProduct.setQuantity(10);
    cartProduct.setUnitPrice(BigDecimal.valueOf(1.50));

    List<CartProductDTO> products = List.of(cartProduct);
    cart.setProducts(products);
  }

  @Test
  @DisplayName("Given a cart, when call updateCart, then it must be called once.")
  public void createCart() throws JsonProcessingException {
    cartService.updateCart(1L, cart);

    cart.setTotalPrice(BigDecimal.valueOf(15.0));
    String cartAsString = objectMapper.writeValueAsString(cart);

    Mockito.verify(redisRepository, Mockito.times(1)).setEx("1", 3600L, cartAsString);
  }

  @Test
  @DisplayName(
      "Given an ID to non existing order, when call getCart, then throws an"
          + " NotFoundException(\"Pedido de compra não encontrado\"")
  public void shouldThrowNotFoundWhenOrderDoesntExist() {

    Mockito.when(redisRepository.get(Mockito.any())).thenReturn(null);

    Exception thrown =
        Assertions.assertThrows(NotFoundException.class, () -> cartService.getCart(10L));

    Assertions.assertEquals("Pedido de compra não encontrado.", thrown.getMessage());
  }

  @Test
  @DisplayName("Given an ID of an order, when call getCart, return that order.")
  public void shouldReturnACart() throws JsonProcessingException, NotFoundException {

    Mockito.when(redisRepository.get(Mockito.any()))
        .thenReturn(objectMapper.writeValueAsString(cart));

    Cart foundCart = cartService.getCart(1L);

    Assertions.assertEquals(foundCart.getStatusCode(), cart.getStatusCode());
    Assertions.assertEquals(
        foundCart.getProducts().get(0).getQuantity(), cart.getProducts().get(0).getQuantity());
    Assertions.assertEquals(
        foundCart.getProducts().get(0).getUnitPrice(), cart.getProducts().get(0).getUnitPrice());
  }

  @Test
  @DisplayName(
      "Given a new status code, when call changeStatus, then the statusCode should be updated.")
  public void shoudUpdateStatus()
      throws NotFoundException, JsonProcessingException, InvalidStatusCodeException {

    Mockito.when(redisRepository.get(Mockito.any()))
        .thenReturn(objectMapper.writeValueAsString(cart));

    Cart updatedCart = cartService.changeStatus(1L, "FINALIZADO");

    Assertions.assertEquals(CartStatusCodeEnum.FINALIZADO, updatedCart.getStatusCode());
  }

  @Test
  @DisplayName("Given a Cart, when call switchStatus, then the statusCode should be switched.")
  public void shouldSwitchStatus() throws JsonProcessingException, NotFoundException {

    Mockito.when(redisRepository.get(Mockito.any()))
        .thenReturn(objectMapper.writeValueAsString(cart));

    CartStatusCodeEnum currentStatus = cart.getStatusCode();

    Cart switchedStatusCart = cartService.switchStatus(1L);

    Assertions.assertNotEquals(currentStatus, switchedStatusCart.getStatusCode());
  }
}
