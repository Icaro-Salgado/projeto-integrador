package br.com.mercadolivre.projetointegrador.marketplace.services;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.CartProductDTO;
import br.com.mercadolivre.projetointegrador.marketplace.enums.CartStatusCodeEnum;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.InvalidStatusCodeException;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Cart;
import br.com.mercadolivre.projetointegrador.marketplace.repository.RedisRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@AllArgsConstructor
public class CartService {

  RedisRepository redisRepository;
  ObjectMapper objectMapper;

  public Cart updateCart(Long id, Cart cart) throws JsonProcessingException {
    cart.setTotalPrice(totalPrice(cart));
    String cartAsString = objectMapper.writeValueAsString(cart);

    redisRepository.setEx(id.toString(), 3600L, cartAsString);
    return cart;
  }

  public Cart getCart(Long id) throws JsonProcessingException, NotFoundException {
    String cartAsString = redisRepository.get(id.toString());
    if (cartAsString == null) {
      throw new NotFoundException("Pedido de compra não encontrado.");
    }
    return objectMapper.readValue(cartAsString, Cart.class);
  }

  private BigDecimal totalPrice(Cart cart) {
    BigDecimal total = BigDecimal.ZERO;
    for (CartProductDTO product : cart.getProducts()) {
      BigDecimal price = product.getUnitPrice().multiply(BigDecimal.valueOf(product.getQuantity()));
      total = total.add(price);
    }
    return total;
  }

  public Cart changeStatus(Long id, String status)
          throws JsonProcessingException, NotFoundException, InvalidStatusCodeException {
    if (!CartStatusCodeEnum.contains(status)) {
      throw new InvalidStatusCodeException("Verifique o status informado.");
    }
    Cart cart = getCart(id);
    CartStatusCodeEnum statusCode = CartStatusCodeEnum.valueOf(status);
    cart.setStatusCode(statusCode);

    updateCart(id, cart);
    return cart;
  }

  public Cart switchStatus(Long id) throws NotFoundException, JsonProcessingException {
    Cart cart = getCart(id);
    cart.setStatusCode(CartStatusCodeEnum.switchStatus(cart.getStatusCode()));
    updateCart(id, cart);
    return cart;
  }
}
