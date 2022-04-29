package br.com.mercadolivre.projetointegrador.marketplace.model;

import br.com.mercadolivre.projetointegrador.marketplace.dtos.CartProductDTO;
import br.com.mercadolivre.projetointegrador.marketplace.enums.CartStatusCodeEnum;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class Cart {
    private LocalDate date;
    private CartStatusCodeEnum statusCode;
    private List<CartProductDTO> products;
    private BigDecimal totalPrice;
}
