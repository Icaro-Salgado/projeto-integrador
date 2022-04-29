package br.com.mercadolivre.projetointegrador.marketplace.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
public class CartProductDTO {
    private Long product_id;
    private int quantity;
    private BigDecimal unitPrice;
}
