package br.com.mercadolivre.projetointegrador.marketplace.model;

import br.com.mercadolivre.projetointegrador.marketplace.dto.CartProductDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Setter
@Getter
public class Cart {
    private LocalDate date;
    private String statusCode;
    private List<CartProductDTO> products;
    private BigDecimal totalPrice;
}
