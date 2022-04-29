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
public class PurchaseOrderDTO extends Cart {
    private Long id;

    public Cart mountCart() {
        Cart cart = new Cart();
        cart.setDate(super.getDate());
        cart.setStatusCode(super.getStatusCode());
        cart.setProducts(super.getProducts());
        return cart;
    }
}