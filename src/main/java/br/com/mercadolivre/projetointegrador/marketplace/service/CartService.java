package br.com.mercadolivre.projetointegrador.marketplace.service;

import br.com.mercadolivre.projetointegrador.marketplace.dto.CartProductDTO;
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
    ObjectMapper objectMapper = new ObjectMapper();

    public Cart updateCart(Long id, Cart cart) throws JsonProcessingException {
        cart.setTotalPrice(totalPrice(cart));
        String cartAsString = objectMapper.writeValueAsString(cart);
        redisRepository.set(String.valueOf(id), cartAsString);
        return cart;
    }

    public Cart getCart(Long id) throws JsonProcessingException {
        String idAsString = String.valueOf(id);
        String cart = redisRepository.get(idAsString);
        Cart cartAsObject = objectMapper.readValue(cart, Cart.class);
        return cartAsObject;
    }

    private BigDecimal totalPrice(Cart cart) {
        BigDecimal total = BigDecimal.ZERO;
        for (CartProductDTO product :
                cart.getProducts()) {
            BigDecimal price = product.getUnitPrice().multiply(BigDecimal.valueOf(product.getQuantity()));
            total = total.add(price);
        }
        return total;
    }
}
