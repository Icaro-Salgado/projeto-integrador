package br.com.mercadolivre.projetointegrador.marketplace.dtos;

import br.com.mercadolivre.projetointegrador.marketplace.model.Cart;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PurchaseOrderDTO {

  private List<CartProductDTO> products;

  public Cart mountCart() {
    Cart cart = new Cart();
    cart.setDate(LocalDate.now());
    cart.setProducts(this.products);
    return cart;
  }
}
