package br.com.mercadolivre.projetointegrador.marketplace.controllers;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.PurchaseOrderDTO;
import br.com.mercadolivre.projetointegrador.marketplace.exceptions.NotFoundException;
import br.com.mercadolivre.projetointegrador.marketplace.model.Cart;
import br.com.mercadolivre.projetointegrador.marketplace.services.CartService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/fresh-products/orders")
public class CartController {

  CartService cartService;

  @PostMapping
  public ResponseEntity<?> orderPrice(
      @RequestBody PurchaseOrderDTO purchaseOrder, UriComponentsBuilder uriBuilder)
      throws JsonProcessingException {
    BigDecimal totalPrice =
        cartService.updateCart(purchaseOrder.getId(), purchaseOrder.mountCart()).getTotalPrice();

    URI uri =
        uriBuilder
            .path("/api/v1/fresh-products/orders/{id}")
            .buildAndExpand(purchaseOrder.getId())
            .toUri();

    return ResponseEntity.created(uri).body(totalPrice);
  }

  @GetMapping("/{buyerId}")
  public ResponseEntity<Cart> showOrder(@PathVariable Long buyerId)
      throws NotFoundException, JsonProcessingException {
    return ResponseEntity.ok(cartService.getCart(buyerId));
  }

  @PutMapping("/{buyerId}")
  public ResponseEntity<Cart> updateOrderStatus(
      @PathVariable Long buyerId, @RequestParam String status)
      throws JsonProcessingException, NotFoundException {
    return ResponseEntity.ok(cartService.changeStatus(buyerId, status));
  }
}
