package br.com.mercadolivre.projetointegrador.marketplace.dtos;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter @Setter
public class PurchaseOrderResponseDTO {

    BigDecimal totalPrice;
}
