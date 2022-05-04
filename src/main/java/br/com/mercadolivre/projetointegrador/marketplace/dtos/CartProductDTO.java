package br.com.mercadolivre.projetointegrador.marketplace.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartProductDTO {
  private Long productId;
  private int quantity;
  private BigDecimal unitPrice;
}
